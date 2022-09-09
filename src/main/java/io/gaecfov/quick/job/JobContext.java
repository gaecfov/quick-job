package io.gaecfov.quick.job;

import io.gaecfov.quick.job.parameter.JobParameters;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Getter
@ToString
@Slf4j(topic = Constants.LOGGER)
public class JobContext {

  private final String runId;
  private final JobParameters parameters;

  public JobContext(JobParameters parameters) {
    this.runId = NanoIdUtils.randomNanoId();
    this.parameters = parameters;
  }
}
