package io.gaecfov.quick.job.listener;

import io.gaecfov.quick.job.step.StepContext;

/**
 * 步骤监听器
 *
 * @author zhangqin
 * @since 2022/8/27
 */
public interface StepListener {

  default void beforeStep(StepContext context) {
  }

  default void onFailed(StepContext context) {
  }

  default void afterStep(StepContext context) {
  }
}
