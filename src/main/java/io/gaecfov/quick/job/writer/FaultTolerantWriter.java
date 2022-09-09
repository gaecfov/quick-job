package io.gaecfov.quick.job.writer;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = "quick.job")
public abstract class FaultTolerantWriter<T> implements Writer<T> {

  protected abstract void write(T item, StepContext stepContext) throws JobIgnoreException, JobSuspendException;

  @Override
  public void write(List<T> data, StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    for (T item : data) {
      try {
        write(item, stepContext);
      } catch (JobIgnoreException | JobSuspendException ex) {
        log.warn("单条数据写异常【中断】：{}", item);
        throw ex;
      } catch (Exception ex) {
        log.warn("单条数据写异常【跳过】：{}", item);
      }
    }
  }
}
