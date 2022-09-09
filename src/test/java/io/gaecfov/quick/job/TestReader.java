package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.reader.Reader;
import io.gaecfov.quick.job.step.StepContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class TestReader implements Reader<TestData> {


  @Override
  public List<TestData> read(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    boolean readed = (boolean) stepContext.getParameters().getOrDefault("readed", false);

    if (!readed) {

      Integer count = (Integer) stepContext.getJobContext().getParameters().getOrDefault("count", 100);

      if (count > 10) {
        throw new JobIgnoreException("不干了...");
      }

      List<TestData> list = new ArrayList<>(count);
      for (long i = 0; i < count; i++) {
        TestData data = new TestData();
        data.setId(i);
        data.setName("name" + i);
        list.add(data);
      }
      stepContext.getParameters().put("readed", true);
      return list;
    }
    return Collections.emptyList();
  }
}
