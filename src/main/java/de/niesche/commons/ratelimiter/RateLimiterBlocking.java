/**
 * 
 */
package de.niesche.commons.ratelimiter;

/**
 * @author hn3000
 *
 */
public class RateLimiterBlocking implements IRateLimiterBlocking {

  /**
   * 
   */
  public RateLimiterBlocking() {
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see de.niesche.commons.ratelimiter.IRateLimiter#consumeAmount()
   */
  @Override
  public boolean consumeAmount() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see de.niesche.commons.ratelimiter.IRateLimiter#consumeAmount(double)
   */
  @Override
  public boolean consumeAmount(double numPermits) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see de.niesche.commons.ratelimiter.IRateLimiter#waitTimeForAmount()
   */
  @Override
  public long waitTimeForAmount() {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see de.niesche.commons.ratelimiter.IRateLimiter#waitTimeForAmount(double)
   */
  @Override
  public long waitTimeForAmount(double numPermits) {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see de.niesche.commons.ratelimiter.IRateLimiterBlocking#waitForAmount(double)
   */
  @Override
  public void waitForAmount(double amount) throws InterruptedException {
    // TODO Auto-generated method stub

  }

}
