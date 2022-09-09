package io.gaecfov.quick.job.listener;

import io.gaecfov.quick.job.step.StepContext;

/**
 * @author zhangqin
 * @since 2022/9/8
 */
public interface ReadWriteStepListener extends StepListener {

  default void beforeRead(StepContext context) {
  }

  default void afterRead(StepContext context) {
  }

  default void beforeWrite(StepContext context) {
  }

  default void afterWrite(StepContext context) {
  }
}
