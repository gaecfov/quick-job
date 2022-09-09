package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobExecuteException;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public interface Job {

  String jobName();
  void execute(JobContext context) throws JobExecuteException;
}
