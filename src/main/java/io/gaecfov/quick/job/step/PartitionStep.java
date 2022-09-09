package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.listener.StepListener;
import io.gaecfov.quick.job.parameter.StepParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;

/**
 * 分区步骤
 *
 * 将一个步骤拆成多个步骤
 *
 * 多个步骤共享上下文
 *
 * @author zhangqin
 * @since 2022/8/28
 */
@Slf4j(topic = Constants.LOGGER)
public class PartitionStep extends AbstractStep<StepListener> {

  private final Step step;
  private TaskExecutor executor;
  private StepPartitioner partitioner;

  public PartitionStep(String stepName, List<StepListener> listeners, Step step, StepPartitioner partitioner) {
    super(stepName, listeners);
    this.step = step;
    this.partitioner = partitioner;
  }

  public PartitionStep(String stepName, List<StepListener> listeners, Step step, StepPartitioner partitioner,
      TaskExecutor executor) {
    super(stepName, listeners);
    this.step = step;
    this.partitioner = partitioner;
    this.executor = executor;
  }

  @Override
  protected void executeStep(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    if (this.executor != null) {
      asyncExecuteInnerStep(stepContext);
    } else {
      syncExecuteInnerStep(stepContext);
    }
  }

  private void asyncExecuteInnerStep(StepContext stepContext) {
    Map<String, StepParameters> map = partitioner.split(stepContext);
    List<CompletableFuture<Void>> futures = new ArrayList<>(map.size());
    Map<String, Boolean> result = new ConcurrentHashMap<>(map.size());
    for (Entry<String, StepParameters> entry : map.entrySet()) {
      futures.add(CompletableFuture.runAsync(() -> {
        try {
          this.step.execute(new StepContext(stepContext.getJobContext(), stepContext, entry.getValue()));
          result.put(entry.getKey(), true);
        } catch (JobSuspendException | JobIgnoreException e) {
          log.warn("{}^^{}分流子步骤^^{}执行异常：{}", stepContext.getJobContext().getRunId(), this.step.stepName(),
              entry.getValue(), e.getMessage());
          result.put(entry.getKey(), false);
        } catch (Exception ex) {
          log.error("{}^^{}分流子步骤^^{}执行异常：{}", stepContext.getJobContext().getRunId(), this.step.stepName(),
              entry.getValue(), ex.getStackTrace());
          result.put(entry.getKey(), false);
        }
      }, this.executor));
    }

    CompletableFuture<Void> all = CompletableFuture.allOf(
        futures.toArray(new CompletableFuture[0]));
    all.join();
    this.partitioner.thenApply(result);
  }

  private void syncExecuteInnerStep(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    Map<String, StepParameters> map = partitioner.split(stepContext);
    for (StepParameters parameters : map.values()) {
      this.step.execute(new StepContext(stepContext.getJobContext(), stepContext, parameters));
    }
  }
}
