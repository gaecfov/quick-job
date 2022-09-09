package io.gaecfov.quick.job.exception;

/**
 * 任务终止异常
 *
 * @author zhangqin
 * @since 2022/8/27
 */
public class JobSuspendException extends Exception {

  public JobSuspendException(String message) {
    super(message);
  }
}
