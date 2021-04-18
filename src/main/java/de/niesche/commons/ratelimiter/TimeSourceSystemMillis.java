package de.niesche.commons.ratelimiter;

import de.niesche.commons.ratelimiter.IRateLimiter.ITimeSource;

public final class TimeSourceSystemMillis implements ITimeSource {
  @Override
  public long currentTimeMillis() {
    return System.currentTimeMillis();
  }
}
