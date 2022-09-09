package io.gaecfov.quick.job.reader;

import java.util.List;
import lombok.Data;

/**
 * @author zhangqin
 * @since 2022/9/9
 */
@Data
public class CursorResult<T> {

  private String cursor;
  private boolean hasNext;
  private List<T> data;
}
