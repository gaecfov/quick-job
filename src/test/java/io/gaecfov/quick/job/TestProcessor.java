package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.processor.Processor;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class TestProcessor implements Processor<TestData, TestData> {
  @Override
  public List<TestData> process(List<TestData> data, StepContext stepContext)
      throws JobSuspendException, JobIgnoreException {
    for (TestData item : data) {
      item.setName(item.getName() + "_p");
    }
    return data;
  }
}
