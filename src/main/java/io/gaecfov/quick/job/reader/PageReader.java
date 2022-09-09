package io.gaecfov.quick.job.reader;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.parameter.StepParameters;
import io.gaecfov.quick.job.step.StepContext;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/9/9
 */
@Slf4j(topic = Constants.LOGGER)
public abstract class PageReader<T> implements Reader<T> {

  public static final String SP_PAGE_KEY = "SP:PAGE";
  public static final String SP_PAGE_COUNT_KEY = "SP:PAGE_COUNT";

  @Override
  public List<T> read(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    StepParameters parameters = stepContext.getParameters();
    int page = parameters.getValueDefault(SP_PAGE_KEY, 1);
    int pageCount = parameters.getValueDefault(SP_PAGE_COUNT_KEY, 1);
    if (page <= pageCount) {
      PageResult<T> result = read(page, stepContext);
      if (log.isDebugEnabled()) {
        log.debug("HasNextReader读取第{}页数据：{}", page, result);
      }
      parameters.setValueIfAbsent(SP_PAGE_COUNT_KEY,
          (result.getTotal() + result.getPageSize() - 1) / result.getPageSize());
      parameters.setValue(SP_PAGE_KEY, page + 1);
      return result.getData();
    }
    return Collections.emptyList();
  }


  protected abstract PageResult<T> read(int page, StepContext stepContext);
}
