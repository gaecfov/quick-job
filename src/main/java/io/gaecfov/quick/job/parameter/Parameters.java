package io.gaecfov.quick.job.parameter;

import java.util.HashMap;

/**
 * @author zhangqin
 * @since 2022/8/27
 */
class Parameters extends HashMap<String, Object> {

  @SuppressWarnings("unchecked")
  public <T> T getValue(String key) {
    Object value = get(key);
    try {
      return (T) value;
    } catch (Exception ex) {
      return null;
    }
  }

  public <T> T getValueDefault(String key, T defaultValue) {
    T t = getValue(key);
    if (t == null) {
      return defaultValue;
    }
    return t;
  }

  public void setValue(String key, Object value) {
    this.put(key, value);
  }

  public void setValueIfAbsent(String key, Object value) {
    this.putIfAbsent(key, value);
  }
}
