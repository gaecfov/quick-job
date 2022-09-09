package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.listener.StepListener;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.task.TaskExecutor;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class PartitionStepBuilder<T> {

  private String name;
  private List<StepListener> listeners;
  private Step step;
  private StepPartitioner partitioner;
  private TaskExecutor taskExecutor;

  public PartitionStepBuilder<T> step(String name) {
    this.name = name;
    return this;
  }

  public PartitionStepBuilder<T> innerStep(Step step) {
    this.step = step;
    return this;
  }


  public PartitionStepBuilder<T> splitter(StepPartitioner partitioner) {
    this.partitioner = partitioner;
    return this;
  }


  public PartitionStepBuilder<T> taskExecutor(TaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
    return this;
  }


  public PartitionStepBuilder<T> listener(StepListener listener) {
    if (listeners == null) {
      this.listeners = new ArrayList<>();
    }
    this.listeners.add(listener);
    return this;
  }

  public PartitionStep build() {
    return new PartitionStep(this.name, this.listeners, this.step, this.partitioner, this.taskExecutor);
  }
}
