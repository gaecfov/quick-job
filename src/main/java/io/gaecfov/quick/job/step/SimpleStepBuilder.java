package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.listener.StepListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class SimpleStepBuilder {

  private String name;
  private Consumer<StepContext> consumer;
  private List<StepListener> listeners;

  public SimpleStepBuilder step(String name) {
    this.name = name;
    return this;
  }

  public SimpleStepBuilder tasklate(Consumer<StepContext> consumer) {
    this.consumer = consumer;
    return this;
  }

  public SimpleStepBuilder listener(StepListener listener) {
    if (listeners == null) {
      this.listeners = new ArrayList<>();
    }
    this.listeners.add(listener);
    return this;
  }

  public SimpleStep build() {
    return new SimpleStep(this.name, this.consumer, this.listeners);
  }
}
