package io.gaecfov.quick.job.step;


import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.listener.StepListener;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public abstract class AbstractStep<L extends StepListener> implements Step {


  private final String stepName;
  private final List<L> listeners;

  public AbstractStep(String stepName, List<L> listeners) {
    this.stepName = stepName;
    this.listeners = listeners;
  }

  @Override
  public String stepName() {
    return this.stepName;
  }

  @Override
  public void execute(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    callListener(listener -> listener.beforeStep(stepContext));
    try {
      executeStep(stepContext);
      callListener(listener -> listener.afterStep(stepContext));
    } catch (Exception ex) {
      callListener(listener -> listener.onFailed(stepContext));
      throw ex;
    }
  }

  protected void callListener(Consumer<L> consumer) {
    if (!Objects.isNull(this.listeners)) {
      for (L listener : this.listeners) {
        try {
          consumer.accept(listener);
        } catch (Exception ex) {
          log.error("执行步骤监听器失败", ex);
        }
      }
    }
  }

  protected abstract void executeStep(StepContext stepContext) throws JobSuspendException, JobIgnoreException;
}
