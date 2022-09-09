package io.gaecfov.quick.job.reader;

import java.util.List;
import lombok.Data;

/**
 * @author zhangqin
 * @since 2022/9/9
 */
@Data
public class HasNextResult<T> {

  private boolean hasNext;
  private List<T> data;
}
