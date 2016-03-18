package de.niesche.commons.ratelimiter.test;

import de.niesche.commons.ratelimiter.IRateLimiter.ITimeSource;

final class TimeSourceAdjustable implements ITimeSource {

  public TimeSourceAdjustable(long now) {
    _time = now;
  }

  @Override
  public long currentTimeMillis() {
    return _time;
  }
  
  public void setCurrentTimeMillis(long time) {
    _time = time;
  }
  public void addMillis(long time) {
    _time += time;
  }

  private long _time;
}
