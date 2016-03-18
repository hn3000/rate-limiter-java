package de.niesche.commons.ratelimiter;

public interface IRateLimiterBlocking extends IRateLimiter {
  void waitForAmount(double amount) throws InterruptedException;
}
