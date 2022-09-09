package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobInstanceException;
import io.gaecfov.quick.job.step.ReadProcessWriteStepBuilder;
import io.gaecfov.quick.job.step.SimpleStepBuilder;
import io.gaecfov.quick.job.step.Step;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Configurable
public class TestJobConfiguration {


  @Bean
  public Job testJob() throws JobInstanceException {
    Step step = new ReadProcessWriteStepBuilder<TestData, TestData>()
        .step("测试Step")
        .reader(new TestReader())
        .processor(new TestProcessor())
        .writer(new TestWriter())
        .build();

    return new SimpleJobBuilder()
        .job("测试Job")
        .next(step)
        .next(new SimpleStepBuilder().step("后续步骤").tasklate((context) -> {
          System.out.println("后续步骤>>" + context.getJobContext().getRunId());
        }).build())
        .build();
  }
}
