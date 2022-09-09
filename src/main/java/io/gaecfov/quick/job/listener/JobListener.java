package io.gaecfov.quick.job.listener;

import io.gaecfov.quick.job.JobContext;
import io.gaecfov.quick.job.exception.JobExecuteException;

/**
 * 任务监听器
 *
 * @author zhangqin
 * @since 2022/8/27
 */
public interface JobListener {

  default void beforeStart(JobContext context) throws JobExecuteException {
  }

  default void onIgnored(JobContext context) throws JobExecuteException {
  }

  default void onFailed(JobContext context) throws JobExecuteException {
  }

  default void afterComplete(JobContext context) throws JobExecuteException {
  }
}
