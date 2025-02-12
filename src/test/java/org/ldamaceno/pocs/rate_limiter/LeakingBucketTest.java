package org.ldamaceno.pocs.rate_limiter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeakingBucketTest {

    @Test
    public void testBucket() throws InterruptedException {
        LeakingBucket bucket = new LeakingBucket(100L, 10L);

        assertTrue(bucket.addPacket(20));
        assertTrue(bucket.addPacket(30));
        assertTrue(bucket.addPacket(50));
        assertFalse(bucket.addPacket(20));
        assertFalse(bucket.addPacket(30));

        Thread.sleep(3000L);

        assertTrue(bucket.addPacket(30));

    }
}
