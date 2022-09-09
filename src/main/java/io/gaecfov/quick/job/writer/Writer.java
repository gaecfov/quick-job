package io.gaecfov.quick.job.writer;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public interface Writer<T> {

  void write(List<T> t, StepContext stepContext) throws JobSuspendException, JobIgnoreException;
}
