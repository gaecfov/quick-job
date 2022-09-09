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
public abstract class CursorReader<T> implements Reader<T> {

  public static final String SP_CURSOR_KEY = "SP:CURSOR";
  public static final String SP_HAS_NEXT_KEY = "SP:HAS_NEXT";

  @Override
  public List<T> read(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    StepParameters parameters = stepContext.getParameters();
    String cursor = parameters.getValue(SP_CURSOR_KEY);
    boolean hasNext = parameters.getValueDefault(SP_HAS_NEXT_KEY, true);
    if (hasNext) {
      CursorResult<T> result = read(cursor, stepContext);
      if (log.isDebugEnabled()) {
        log.debug("CursorReader读取{}数据：{}", cursor, result.getData());
      }
      parameters.setValue(SP_CURSOR_KEY, result.getCursor());
      parameters.setValue(SP_HAS_NEXT_KEY, result.isHasNext());
      return result.getData();
    }
    return Collections.emptyList();
  }

  protected abstract CursorResult<T> read(String cursor, StepContext stepContext);
}
