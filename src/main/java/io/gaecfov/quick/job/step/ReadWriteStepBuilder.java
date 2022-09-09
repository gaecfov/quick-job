package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.listener.ReadWriteStepListener;
import io.gaecfov.quick.job.reader.Reader;
import io.gaecfov.quick.job.writer.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class ReadWriteStepBuilder<T> {

  private String name;
  private Reader<T> reader;
  private Writer<T> writer;
  private List<ReadWriteStepListener> listeners;

  public ReadWriteStepBuilder<T> step(String name) {
    this.name = name;
    return this;
  }

  public ReadWriteStepBuilder<T> reader(Reader<T> reader) {
    this.reader = reader;
    return this;
  }

  public ReadWriteStepBuilder<T> writer(Writer<T> writer) {
    this.writer = writer;
    return this;
  }

  public ReadWriteStepBuilder<T> listener(ReadWriteStepListener listener) {
    if (listeners == null) {
      this.listeners = new ArrayList<>();
    }
    this.listeners.add(listener);
    return this;
  }

  public ReadWriteStep<T> build() {
    return new ReadWriteStep<>(this.name, this.listeners, this.reader, this.writer);
  }
}
