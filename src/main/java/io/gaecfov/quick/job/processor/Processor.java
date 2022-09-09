package io.gaecfov.quick.job.processor;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public interface Processor<T, R> {

  List<R> process(List<T> data, StepContext stepContext) throws JobSuspendException, JobIgnoreException;
}
