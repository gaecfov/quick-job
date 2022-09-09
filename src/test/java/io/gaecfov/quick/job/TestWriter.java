package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import io.gaecfov.quick.job.writer.Writer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public class TestWriter implements Writer<TestData> {

  @Override
  public void write(List<TestData> t, StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    for (TestData testData : t) {
      log.info("write>>{}", testData);
    }
  }
}
