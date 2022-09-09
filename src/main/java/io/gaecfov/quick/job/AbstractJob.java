package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobExecuteException;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.listener.JobListener;
import io.gaecfov.quick.job.step.Step;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j
public abstract class AbstractJob implements Job {

  private final String jobName;
  private final List<Step> steps;
  private final List<JobListener> listeners;
  private final StopWatch stopWatch;

  protected AbstractJob(String jobName, List<Step> steps, List<JobListener> listeners) {
    this.jobName = jobName;
    this.steps = steps;
    this.listeners = listeners;
    this.stopWatch = new Slf4JStopWatch(jobName);
  }

  @Override
  public String jobName() {
    return this.jobName;
  }

  private void stopTag(String tag) {
    this.stopWatch.stop(this.jobName + "." + tag);
  }


  private void beforeStart(JobContext context) throws JobExecuteException {
    if (!Objects.isNull(this.listeners)) {
      for (JobListener jobListener : this.listeners) {
        jobListener.beforeStart(context);
      }
    }
  }

  private void onIgnored(JobContext context) throws JobExecuteException {
    if (!Objects.isNull(this.listeners)) {
      for (JobListener jobListener : this.listeners) {
        jobListener.onIgnored(context);
      }
    }
  }

  private void onFailed(JobContext context) throws JobExecuteException {
    if (!Objects.isNull(this.listeners)) {
      for (JobListener jobListener : this.listeners) {
        jobListener.onFailed(context);
      }
    }
  }

  @Override
  public void execute(JobContext context) throws JobExecuteException {
    beforeStart(context);
    stopTag("beforeStart");
    try {
      runSteps(context);
      stopTag("steps");
    } catch (JobIgnoreException e) {
      onIgnored(context);
      stopTag("ignore");
    } catch (JobSuspendException e) {
      onFailed(context);
      stopTag("failed");
    }
  }

  private void runSteps(JobContext context) throws JobIgnoreException, JobSuspendException {
    for (Step step : this.steps) {
      log.info("[{}]:[{}]:[{}]开始执行...", this.jobName, context.getRunId(), step.stepName());
      StepContext stepContext = new StepContext(context);
      try {
        step.execute(stepContext);
      } catch (JobIgnoreException | JobSuspendException e) {
        log.warn("[{}]:[{}]:[{}]执行中断：{}...", this.jobName, context.getRunId(), step.stepName(), e.getMessage());
        throw e;
      } catch (Exception e) {
        log.warn("[{}]:[{}]:[{}]未知异常,继续执行其他Step：{}...{}", this.jobName, context.getRunId(), step.stepName(),
            e.getMessage(),
            e.getStackTrace());
      } finally {
        stopTag(step.stepName());
      }
    }
  }

  private void callListener(Consumer<JobListener> consumer) throws JobExecuteException {
    if (!Objects.isNull(this.listeners)) {
      for (JobListener jobListener : this.listeners) {
        try {
          consumer.accept(jobListener);
        } catch (Exception ex) {
          log.error("执行任务监听器失败", ex);
        }
      }
    }
  }
}
