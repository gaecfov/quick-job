package io.gaecfov.quick.job.step;

/**
 * @author zhangqin
 * @since 2022/9/9
 */
@FunctionalInterface
public interface StepFunction<T, R> {

  R apply(T t, StepContext stepContext);
}
