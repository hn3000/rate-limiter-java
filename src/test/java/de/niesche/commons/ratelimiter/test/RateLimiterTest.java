package de.niesche.commons.ratelimiter.test;

import org.junit.Assert;
import org.junit.Test;

import de.niesche.commons.ratelimiter.IRateLimiter;
import de.niesche.commons.ratelimiter.RateLimiterTokenBucket;

/**
 * Unit Tests for the RateLimiterTokenBucket.
 */
public class RateLimiterTest {
  /**
   * Create the test case
   *
   * @param testName
   *          name of the test case
   */
  public RateLimiterTest() {
    _timeSourceZero = new TimeSourceZero();
  }

  @Test
  public void testRateLimiterCreationWorksWithoutTimeSourceAndInitAmount() {
    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 5.0);
    Assert.assertNotNull(limiter);

    // we should never take 50ms to get here ...
    Assert.assertTrue(limiter.waitTimeForAmount(1.0) <= 1000);
    Assert.assertTrue(limiter.waitTimeForAmount(1.0) >= 950);
  }

  @Test
  public void testRateLimiterCreationWorksWithoutTimeSource() {
    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 5.0, 0.0);
    Assert.assertNotNull(limiter);

    // we should never take 50ms to get here ...
    Assert.assertTrue(limiter.waitTimeForAmount(1.0) <= 1000);
    Assert.assertTrue(limiter.waitTimeForAmount(1.0) >= 950);
  }

  @Test
  public void testRateLimiterCreationWorksWithTimeSource() {
    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 5.0, 0.0, _timeSourceZero);
    Assert.assertNotNull(limiter);

    // time is fixed (thru time source), result must be exact
    Assert.assertEquals(1000, limiter.waitTimeForAmount(1.0));
  }

  @Test
  public void testRateLimiterGivesOutInitAmount() {
    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 1.0, 1.0, _timeSourceZero);

    Assert.assertTrue(limiter.consumeAmount());
    Assert.assertFalse(limiter.consumeAmount());
  }

  @Test
  public void testRateLimiterDoesNotWaitForInitAmount() {
    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 1.0, 1.0, _timeSourceZero);

    Assert.assertEquals(0, limiter.waitTimeForAmount(1.0));
    Assert.assertEquals(0, limiter.waitTimeForAmount());
  }

  
  @Test
  public void testRateLimiterGivesFreshAmountAfterSomeTime() {
    TimeSourceAdjustable timeSource = new TimeSourceAdjustable(0);

    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 1.0, 0.0, timeSource);

    Assert.assertEquals(1000, limiter.waitTimeForAmount(1.0));
    Assert.assertFalse(limiter.consumeAmount());
    timeSource.setCurrentTimeMillis(999);
    Assert.assertFalse(limiter.consumeAmount());

    timeSource.setCurrentTimeMillis(1000);
    Assert.assertTrue(limiter.consumeAmount());

    Assert.assertFalse(limiter.consumeAmount());
  }

  @Test
  public void testRateLimiterDoesNotWaitIfAmountIsAvailable() {
    TimeSourceAdjustable timeSource = new TimeSourceAdjustable(0);

    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 1.0, 1.0, timeSource);
    Assert.assertEquals(0, limiter.waitTimeForAmount(1.0));
  }

  @Test
  public void testRateLimiterDoesNotWaitIfAmountBecomesAvailable() {
    TimeSourceAdjustable timeSource = new TimeSourceAdjustable(0);

    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 2.0, 1.0, timeSource);
    Assert.assertEquals(0, limiter.waitTimeForAmount(1.0));
    Assert.assertEquals(1000, limiter.waitTimeForAmount(2.0));
    timeSource.addMillis(1000);
    Assert.assertEquals(0, limiter.waitTimeForAmount(2.0));
  }

  @Test
  public void testRateLimiterGivesImpossibleTimeForTooLargeAmount() {
    TimeSourceAdjustable timeSource = new TimeSourceAdjustable(0);

    IRateLimiter limiter = RateLimiterTokenBucket.create(1.0, 2.0, 1.0, timeSource);
    Assert.assertEquals(IRateLimiter.WAIT_IMPOSSIBLE, limiter.waitTimeForAmount(2.1));
  }

  @Test
  public void testRateLimiterGivesImpossibleTimeForTooLargeWaitTime() {
    TimeSourceAdjustable timeSource = new TimeSourceAdjustable(0);

    IRateLimiter limiter = RateLimiterTokenBucket.create(1e-9, 1e9, 0.0, timeSource);
    Assert.assertEquals(IRateLimiter.WAIT_IMPOSSIBLE, limiter.waitTimeForAmount(1e9));
  }

  private IRateLimiter.ITimeSource _timeSourceZero;
}
