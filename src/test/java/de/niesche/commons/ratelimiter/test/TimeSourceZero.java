package de.niesche.commons.ratelimiter.test;

import de.niesche.commons.ratelimiter.IRateLimiter.ITimeSource;

final class TimeSourceZero implements ITimeSource {
  @Override
  public long currentTimeMillis() {
    return 0;
  }
}
