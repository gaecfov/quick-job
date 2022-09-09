package io.gaecfov.quick.job;


import io.gaecfov.quick.job.parameter.JobParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestJobConfiguration.class)
public class JobLauncherTest {

  @Autowired
  Job testJob;

  @Test
  public void test() {
    JobParameters parameters = new JobParameters();
    parameters.put("count", 5);
    JobContext jobContext = new JobContext(parameters);
    JobLauncher.run(testJob, jobContext);
  }
}