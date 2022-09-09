package io.gaecfov.quick.job.step;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.listener.ReadProcessWriteStepListener;
import io.gaecfov.quick.job.processor.Processor;
import io.gaecfov.quick.job.reader.Reader;
import io.gaecfov.quick.job.writer.Writer;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

/**
 * 管道步骤
 * 读>处理>写
 *
 * @author zhangqin
 * @since 2022/8/27
 */
@Slf4j(topic = Constants.LOGGER)
public class ReadProcessWriteStep<T, R> extends AbstractStep<ReadProcessWriteStepListener> {

  private final Reader<T> reader;
  private final Processor<T, R> processor;
  private final Writer<R> writer;

  private final StopWatch stopWatch;

  public ReadProcessWriteStep(String stepName, List<ReadProcessWriteStepListener> listeners, Reader<T> reader,
      Processor<T, R> processor,
      Writer<R> writer) {
    super(stepName, listeners);
    this.reader = reader;
    this.processor = processor;
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
      if (Objects.isNull(data) || data.isEmpty()) {
        break;
      }
      if (log.isDebugEnabled()) {
        log.debug("{}^^Read^^{}条数据", stepContext, data.size());
      }
      tag("read", stepContext.getParameters().toString());
      callListener(listener -> listener.afterRead(stepContext));


      callListener(listener -> listener.beforeProcess(stepContext));
      List<R> processedData = processor.process(data, stepContext);
      tag("process", stepContext.getParameters().toString());
      if (Objects.isNull(processedData) || processedData.isEmpty()) {
        break;
      }
      callListener(listener -> listener.afterStep(stepContext));

      callListener(listener -> listener.beforeWrite(stepContext));
      writer.write(processedData, stepContext);
      tag("process", stepContext.getParameters().toString());
      callListener(listener -> listener.afterProcess(stepContext));
    }
  }
}
