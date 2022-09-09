package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.JobContext;
import io.gaecfov.quick.job.parameter.StepParameters;
import lombok.Getter;
import lombok.ToString;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Getter
@ToString
public class StepContext {

  private final StepParameters parameters;
  private final JobContext jobContext;
  private StepContext parent;

  public StepContext(JobContext jobContext) {
    this.jobContext = jobContext;
    this.parameters = new StepParameters();
  }

  public StepContext(JobContext jobContext, StepParameters parameters) {
    this.jobContext = jobContext;
    this.parameters = parameters;
  }

  public StepContext(JobContext jobContext, StepContext parent, StepParameters parameters) {
    this.jobContext = jobContext;
    this.parent = parent;
    this.parameters = parameters;
  }

  /**
   * 获取任务参数
   *
   * @param key 参数KEY
   * @param <T> 类型
   * @return 返回值
   */
  public <T> T getJobParameter(String key) {
    return this.jobContext.getParameters().getValue(key);
  }

  public <T> T getJobParameter(String key, T defaultValue) {
    return this.jobContext.getParameters().getValueDefault(key, defaultValue);
  }
}

