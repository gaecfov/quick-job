package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.parameter.StepParameters;
import java.util.Map;

/**
 * 步骤分割器
 *
 * @author zhangqin
 * @since 2022/8/28
 */
public interface StepPartitioner {

  /**
   * 分割步骤
   * @param stepContext 步骤上下文
   * @return 子步骤参数
   */
  Map<String, StepParameters> split(StepContext stepContext);

  /**
   * 结果判断
   * @param map 分流的处理结果
   */
  default void thenApply(Map<String, Boolean> map) {
  }
}
