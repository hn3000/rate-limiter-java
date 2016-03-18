package de.niesche.commons.ratelimiter;

/**
 * An IRateLimiter provides a way to rate limit events. Each event is assigned 
 * an amount of some imaginary resource and the IRateLimiter restricts the 
 * amount per time that can be consumed.
 * 
 * If no amount is specified, each event is worth an amount of 1.0 and the 
 * configuration of the specific IRateLimiter implementation would specify 
 * the rate at which consumable amount is created.
 * 
 * The inner interface ITimeSource should be used by implementations
 * to make them testable.
 * 
 * If a rate limiter will never allow an event of a certain amount, it
 * will return a waiting time of WAIT_IMPOSSIBLE from waitTimeForAmount()
 * and consumeAmount() will always return false.
 * 
 * This type of rate limiter can be used to rate limit incoming API calls
 * by dropping calls that exceed the limit. It can also be used to limit
 * outgoing API calls by waiting the time returned by waitTimeForAmount(),
 * since this call does not consume the amount, this can only work properly
 * in single-threaded applications when using implementations of this 
 * interface.
 * 
 * @author Harald Niesche
 *
 */
public interface IRateLimiter {
	boolean consumeAmount();
	boolean consumeAmount(double numPermits);
    long waitTimeForAmount();
    long waitTimeForAmount(double numPermits);
    
	public interface ITimeSource {
	  long currentTimeMillis();
	}
	
	public static long WAIT_IMPOSSIBLE = Long.MAX_VALUE;
}
