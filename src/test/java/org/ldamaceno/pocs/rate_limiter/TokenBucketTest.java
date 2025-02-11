package org.ldamaceno.pocs.rate_limiter;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenBucketTest {

    @Test
    public void testBucket() throws InterruptedException {
        TokenBucket bucket = new TokenBucket(10, 5);

        for(int i = 0; i < 11; i++){
            assertTrue(bucket.consume());
        }

        for(int i = 0; i < 10; i++){
            assertFalse(bucket.consume());
        }

        Thread.sleep(1000);

        for(int i = 0; i < 5; i++){
            assertTrue(bucket.consume());
        }

        for(int i = 0; i < 10; i++){
            assertFalse(bucket.consume());
        }

    }
}
