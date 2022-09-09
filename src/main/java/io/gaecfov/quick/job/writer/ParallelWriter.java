package io.gaecfov.quick.job.writer;

import io.gaecfov.quick.job.step.StepContext;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;

/**
 * 并行写入
 *
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = "quick.job")
public abstract class ParallelWriter<T> implements Writer<T> {

  private final TaskExecutor executor;

  public ParallelWriter(TaskExecutor executor) {
    this.executor = executor;
  }

  protected abstract void writeItem(T t, StepContext context);

  @Override
  public void write(List<T> data, StepContext stepContext) {
    CompletableFuture<Void> all = CompletableFuture.allOf(
        data.stream().map(
                t -> CompletableFuture.runAsync(() -> writeItem(t, stepContext), executor))
            .toArray(CompletableFuture[]::new));
    all.join();
  }
}
