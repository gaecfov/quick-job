package io.gaecfov.quick.job.processor;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public class NothingProcessor<T> implements Processor<T, T> {


  @Override
  public List<T> process(List<T> data, StepContext stepContext) {
    return data;
  }
}
