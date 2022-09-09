package io.gaecfov.quick.job.reader;

import java.util.List;
import lombok.Data;

/**
 * @author zhangqin
 * @since 2022/9/9
 */
@Data
public class PageResult<T> {

  private int total;
  private int page;
  private int pageSize;
  private List<T> data;
}
