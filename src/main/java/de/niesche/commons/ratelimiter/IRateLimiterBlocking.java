package de.niesche.commons.ratelimiter;

/**
 * Interface for blocking / waiting implementations of IRateLimiter.
 * 
 * @author Harald Niesche
 *
 */
public interface IRateLimiterBlocking extends IRateLimiter {
  /**
   * Waits until amount becomes available and consumes it.
   * Multiple threads can safely use this call and expect to
   * be treated fairly.
   * @param amount
   * @throws InterruptedException
   */
  void waitForAmount(double amount) throws InterruptedException;

  /** Consumes 1.0 amount, returns false if that is not possible. */
  boolean consumeAmount();
  /** Consumes amount, returns false if that is not possible. */
  boolean consumeAmount(double amount);

  /** 
   * Gives time required to wait until amount 1.0 can be consumed.
   * The call does not consume the amount and should not be used
   * from multiple threads, since nothing prevents starvation of
   * threads.
   * Returns WAIT_IMPOSSIBLE if it will never allow the amount. 
   */
  long waitTimeForAmount();

  /** 
   * Gives time required to wait until amount can be consumed. 
   * The call does not consume the amount and should not be used
   * from multiple threads, since nothing prevents starvation of
   * threads.
   * Returns WAIT_IMPOSSIBLE if it will never allow the amount. 
   */
  long waitTimeForAmount(double amount);
  

}
