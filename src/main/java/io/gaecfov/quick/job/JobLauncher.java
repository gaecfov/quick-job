package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobExecuteException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public class JobLauncher {

  public static void run(Job job, JobContext context) {
    try {
      MDC.put("jobId", context.getRunId());
      MDC.put("jobName", job.jobName());
      job.execute(context);
    } catch (JobExecuteException ex) {
      log.error("执行失败:{}", ex.getMessage());
    } catch (Exception ex) {
      log.error("执行异常:{} {}", ex.getMessage(), ex.getStackTrace());
    } finally {
      MDC.remove("jobId");
      MDC.remove("jobName");
    }
  }
}
