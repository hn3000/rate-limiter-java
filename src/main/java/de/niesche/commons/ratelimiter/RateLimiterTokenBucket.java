package de.niesche.commons.ratelimiter;

/**
 * Implementation of the IRateLimiter interface using a modified version of 
 * the Token Bucket algorithm.
 * 
 * 
 * 
 * A good description of the algorithm can be found at 
 * https://en.wikipedia.org/wiki/Token_bucket .
 * 
 * 
 * 
 * @author Harald Niesche
 *
 */
public class RateLimiterTokenBucket implements IRateLimiter {

  public static IRateLimiter create(double ratePerSecond, double maxAmount) {
    return create(ratePerSecond, maxAmount, 0.0, s_defaultTimeSource);
  }
  public static IRateLimiter create(double ratePerSecond, double maxAmount, double initAmount) {
    return create(ratePerSecond, maxAmount, initAmount, s_defaultTimeSource);
  }
  public static IRateLimiter create(double ratePerSecond, double maxAmount, double initAmount, ITimeSource source) {
    return new RateLimiterTokenBucket(ratePerSecond, maxAmount, initAmount, source);
  }

  public RateLimiterTokenBucket(double ratePerSecond, double maxAmount, double initAmount, ITimeSource timeSource) {
    _ratePerMillisecond = ratePerSecond / 1000.0;
    _maxAmount = maxAmount;
    _currentAmount = initAmount;
    _timeSource = timeSource;
    _lastTimeMillis = timeSource.currentTimeMillis();
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.niesche.commons.ratelimiter.IRateLimiter#consumeAmount()
   */
  @Override
  public boolean consumeAmount() {
    return consumeAmount(1.0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.niesche.commons.ratelimiter.IRateLimiter#consumeAmount(double)
   */
  @Override
  public boolean consumeAmount(double numPermits) {
    _updateAmount();
    boolean success = _currentAmount >= numPermits;
    if (success) {
      _currentAmount -= numPermits;
    }
    return success;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see de.niesche.commons.ratelimiter.IRateLimiter#waitTimeForAmount()
   */
  @Override
  public long waitTimeForAmount() {
    return waitTimeForAmount(1.0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.niesche.commons.ratelimiter.IRateLimiter#waitTimeForAmount(double)
   */
  @Override
  public long waitTimeForAmount(double numPermits) {
    if (numPermits <= _currentAmount) {
      return 0;
    }
    _updateAmount();

    if (numPermits <= _currentAmount) {
      return 0;
    }
    if (numPermits > _maxAmount) {
      return IRateLimiter.WAIT_IMPOSSIBLE;
    }
    
    double lack = numPermits - _currentAmount;
    double waitTime = Math.ceil(lack / _ratePerMillisecond);
    if (waitTime > IRateLimiter.WAIT_IMPOSSIBLE) {
      return IRateLimiter.WAIT_IMPOSSIBLE;
    }
    return (long)waitTime;
  }

  private void _updateAmount() {
    long now = _timeSource.currentTimeMillis();
    long delta = now - _lastTimeMillis;
    if (delta > 0) {
      double adjust = delta * _ratePerMillisecond;
      adjust += _currentAmount;
      _currentAmount = Math.min(adjust, _maxAmount);
      _lastTimeMillis = now;
    }
  }

  private double _ratePerMillisecond;
  private double _maxAmount;
  private double _currentAmount;
  private long   _lastTimeMillis;
  
  private ITimeSource _timeSource;
  
  private static ITimeSource s_defaultTimeSource = new TimeSourceSystemMillis();

}
