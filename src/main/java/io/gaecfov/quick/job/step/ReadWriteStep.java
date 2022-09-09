package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.listener.ReadWriteStepListener;
import io.gaecfov.quick.job.reader.Reader;
import io.gaecfov.quick.job.writer.Writer;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public class ReadWriteStep<T> extends AbstractStep<ReadWriteStepListener> {

  private final Reader<T> reader;
  private final Writer<T> writer;
  private final StopWatch stopWatch;

  public ReadWriteStep(String stepName, List<ReadWriteStepListener> listeners, Reader<T> reader,
      Writer<T> writer) {
    super(stepName, listeners);
    this.reader = reader;
    this.writer = writer;
    this.stopWatch = new Slf4JStopWatch(stepName);
  }

  private void tag(String tag, String message) {
    this.stopWatch.stop(tag, message);
  }

  @Override
  protected void executeStep(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    while (true) {
      callListener(listener -> listener.beforeRead(stepContext));
      List<T> data = reader.read(stepContext);
      log.info("{}^^Read^^{}条数据", stepContext, data != null ? data.size() : 0);
      if (Objects.isNull(data) || data.isEmpty()) {
        break;
      }
      tag("read", stepContext.getParameters().toString());
      callListener(listener -> listener.beforeRead(stepContext));

      callListener(listener -> listener.beforeWrite(stepContext));
      writer.write(data, stepContext);
      log.info("{}^^Write{}条数据", stepContext, 0);
      tag("write", stepContext.getParameters().toString());
      callListener(listener -> listener.afterWrite(stepContext));
    }
  }
}
