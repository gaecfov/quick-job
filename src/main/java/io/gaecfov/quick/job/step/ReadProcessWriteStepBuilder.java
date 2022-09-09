package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.listener.ReadProcessWriteStepListener;
import io.gaecfov.quick.job.processor.Processor;
import io.gaecfov.quick.job.reader.Reader;
import io.gaecfov.quick.job.writer.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
public class ReadProcessWriteStepBuilder<T, R> {

  private String name;
  private Reader<T> reader;
  private Processor<T, R> processor;
  private Writer<R> writer;
  private List<ReadProcessWriteStepListener> listeners;

  public ReadProcessWriteStepBuilder<T, R> step(String name) {
    this.name = name;
    return this;
  }

  public ReadProcessWriteStepBuilder<T,R> reader(Reader<T> reader) {
    this.reader = reader;
    return this;
  }

  public ReadProcessWriteStepBuilder<T,R> processor(Processor<T, R> processor) {
    this.processor = processor;
    return this;
  }

  public ReadProcessWriteStepBuilder<T,R> writer(Writer<R> writer) {
    this.writer = writer;
    return this;
  }

  public ReadProcessWriteStepBuilder<T,R> listener(ReadProcessWriteStepListener listener) {
    if (listeners == null) {
      this.listeners = new ArrayList<>();
    }
    this.listeners.add(listener);
    return this;
  }

  public ReadProcessWriteStep<T,R> build() {
    return new ReadProcessWriteStep<>(this.name, this.listeners, this.reader, this.processor, this.writer);
  }
}
