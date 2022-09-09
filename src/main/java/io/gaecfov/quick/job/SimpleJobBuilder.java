package io.gaecfov.quick.job;

import io.gaecfov.quick.job.exception.JobInstanceException;
import io.gaecfov.quick.job.listener.JobListener;
import io.gaecfov.quick.job.step.Step;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
@Getter
public class SimpleJobBuilder {

  private String name;
  private List<Step> steps;
  private List<JobListener> listeners;

  public SimpleJobBuilder job(String name) {
    this.name = name;
    return this;
  }

  public SimpleJobBuilder next(Step step) {
    if (this.steps == null) {
      this.steps = new ArrayList<>();
    }
    this.steps.add(step);
    return this;
  }

  public SimpleJobBuilder listener(JobListener listener) {
    if (this.listeners == null) {
      this.listeners = new LinkedList<>();
    }
    this.listeners.add(listener);
    return this;
  }

  public SimpleJob build() throws JobInstanceException {
    return new SimpleJob(this.name, this.steps, this.listeners);
  }
}
