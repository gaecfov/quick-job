package io.gaecfov.quick.job.processor;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public abstract class FaultTolerantProcessor<T, R> implements Processor<T, R> {

  @Override
  public List<R> process(List<T> data, StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    List<R> items = new ArrayList<>(data.size());
    for (T item : data) {
      try {
        items.add(process(item, stepContext));
      } catch (JobIgnoreException | JobSuspendException ex) {
        log.warn("[{}]单条数据处理异常【中断】：{}", stepContext.getJobContext().getRunId(), item);
        throw ex;
      } catch (Exception ex) {
        log.warn("[{}]单条数据处理异常【跳过】：{}", stepContext.getJobContext().getRunId(), item);
      }
    }
    return items;
  }

  protected abstract R process(T item, StepContext stepContext) throws JobIgnoreException, JobSuspendException;
}
