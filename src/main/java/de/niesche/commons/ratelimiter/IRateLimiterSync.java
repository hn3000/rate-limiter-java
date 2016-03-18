package de.niesche.commons.ratelimiter;

/**
 * A version of the IRateLimiter interface where implementations must
 * make sure that it's safe to call them from multiple threads.
 * 
 * Since waitTimeForAmount can not be supported without the risk
 * of starvation, this interface does not provide the methods.
 * 
 * If implementations were to provide the method, they would not be
 * able to ensure fairness. Use IRateLimiterBlocking for those cases. 
 *  
 * @author Harald Niesche
 *
 */
public interface IRateLimiterSync {

  /** Consumes 1.0 amount, returns false if that is not possible. */
  boolean consumeAmount();
  /** Consumes amount, returns false if that is not possible. */
  boolean consumeAmount(double amount);

}
