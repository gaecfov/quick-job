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
 * 一次性Reader，执行一次再执行返回空数据
 *
 * @author zhangqin
 * @since 2022/9/8
 */
@Slf4j(topic = Constants.LOGGER)
public abstract class DisposableReader<T> implements Reader<T> {

  public static final String SP_READ_KEY = "RP:READ";

  @Override
  public List<T> read(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    StepParameters parameters = stepContext.getParameters();
    boolean read = parameters.getValueDefault(SP_READ_KEY, false);
    if (read) {
      return Collections.emptyList();
    }
    parameters.setValue(SP_READ_KEY, true);
    return doRead(stepContext);
  }

  protected abstract List<T> doRead(StepContext stepContext);
}
