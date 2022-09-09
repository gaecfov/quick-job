package io.gaecfov.quick.job.processor;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.step.StepContext;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;

/**
 * 并行处理
 *
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public abstract class ParallelProcessor<T, R> implements Processor<T, R> {

  private final TaskExecutor executor;

  public ParallelProcessor(TaskExecutor executor) {
    this.executor = executor;
  }

  protected abstract R process(T item, StepContext stepContext);

  @Override
  public List<R> process(List<T> data, StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    List<CompletableFuture<R>> futures = data.stream().map(
            t -> CompletableFuture.supplyAsync(() -> process(t, stepContext), executor))
        .collect(Collectors.toList());
    CompletableFuture<Void> all = CompletableFuture.allOf(
        futures.toArray(new CompletableFuture[0]));
    return all.thenApply(
        e -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
  }
}
