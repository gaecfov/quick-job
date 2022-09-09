package io.gaecfov.quick.job.reader;

import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;

/**
 * 读取器
 *
 * @author zhangqin
 * @since 2022/8/27
 */
public interface Reader<T> {

  List<T> read(StepContext stepContext) throws JobSuspendException, JobIgnoreException;
}
