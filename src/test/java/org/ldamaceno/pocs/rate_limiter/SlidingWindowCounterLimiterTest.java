package org.ldamaceno.pocs.rate_limiter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlidingWindowCounterLimiterTest {

    @Test
    public void testRateLimiter() throws InterruptedException {
        SlidingWindowCounterRateLimiter rateLimiter = new SlidingWindowCounterRateLimiter(10, TimeUnit.SECONDS.toMillis(5));

        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.allowRequest("client1"));
        }

        for (int i = 1; i < 10; i++) {
            assertFalse(rateLimiter.allowRequest("client1"));
            assertTrue(rateLimiter.allowRequest("client2"));
        }

        Thread.sleep(5000L);

        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.allowRequest("client1"));
            assertTrue(rateLimiter.allowRequest("client2"));
        }

        for (int i = 1; i < 10; i++) {
            assertFalse(rateLimiter.allowRequest("client1"));
            assertFalse(rateLimiter.allowRequest("client2"));
        }


    }
}
