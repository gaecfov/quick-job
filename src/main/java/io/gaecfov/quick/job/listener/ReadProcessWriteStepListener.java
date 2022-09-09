package io.gaecfov.quick.job.listener;

import io.gaecfov.quick.job.step.StepContext;

/**
 * @author zhangqin
 * @since 2022/9/8
 */
public interface ReadProcessWriteStepListener extends ReadWriteStepListener{
  default void beforeProcess(StepContext context) {
  }

  default void afterProcess(StepContext context) {
  }
}
