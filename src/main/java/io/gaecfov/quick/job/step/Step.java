package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;

/**
 * 步骤
 *
 * @author zhangqin
 * @since 2022/8/27
 */
public interface Step {

  String stepName();

  void execute(StepContext stepContext) throws JobSuspendException, JobIgnoreException;
}
