package io.gaecfov.quick.job;

import io.gaecfov.quick.job.listener.JobListener;
import io.gaecfov.quick.job.step.Step;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class SimpleJob extends AbstractJob{

  protected SimpleJob(String jobName, List<Step> steps,
      List<JobListener> listeners) {
    super(jobName, steps, listeners);
  }
}
