package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.listener.StepListener;
import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单步骤
 *
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public class SimpleStep extends AbstractStep<StepListener> {

  private final Consumer<StepContext> consumer;

  public SimpleStep(String stepName, Consumer<StepContext> consumer, List<StepListener> listeners) {
    super(stepName, listeners);
    this.consumer = consumer;
  }

  @Override
  protected void executeStep(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    consumer.accept(stepContext);
  }
}
