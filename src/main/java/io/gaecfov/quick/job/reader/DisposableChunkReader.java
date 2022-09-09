package io.gaecfov.quick.job.reader;

import io.gaecfov.quick.job.Constants;
import io.gaecfov.quick.job.exception.JobIgnoreException;
import io.gaecfov.quick.job.exception.JobSuspendException;
import io.gaecfov.quick.job.parameter.StepParameters;
import io.gaecfov.quick.job.step.StepContext;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 一次性分块Reader
 * 块大小可在Job参数中定义
 * 执行一次再执行返回空数据
 *
 * @author zhangqin
 * @since 2022/9/8
 */
@Slf4j(topic = Constants.LOGGER)
public abstract class DisposableChunkReader<T> implements Reader<T> {

  public static final String SP_READ_KEY = "RP:READ";
  public static final String SP_BATCH_SIZE_KEY = "RP:CHUNK_SIZE";

  @Override
  public List<T> read(StepContext stepContext) throws JobSuspendException, JobIgnoreException {
    StepParameters parameters = stepContext.getParameters();
    boolean read = parameters.getValueDefault(SP_READ_KEY, false);
    log.info("{}执行read:{}", stepContext.getJobContext().getRunId(), read);
    if (read) {
      return Collections.emptyList();
    }

    int size = parameters.getValueDefault(SP_BATCH_SIZE_KEY, 200);
    List<T> list = readChunk(size, stepContext);
    parameters.setValue(SP_READ_KEY, true);
    return list;
  }

  /**
   * 读取数
   * @param chunkSize 分块大小
   * @param stepContext 步骤上下文
   * @return
   */
  protected abstract List<T> readChunk(int chunkSize, StepContext stepContext);
}
