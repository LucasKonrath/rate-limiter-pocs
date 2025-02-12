package org.ldamaceno.pocs.rate_limiter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class LeakingBucket {
    private final Long maxSize;
    private final long leakRate;
    private long currentSize;
    private final ScheduledExecutorService scheduledExecutorService;

    public LeakingBucket(Long maxSize, long leakRate) {
        this.maxSize = maxSize;
        this.leakRate = leakRate;
        this.currentSize = 0;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(this::leak, 0, 1, TimeUnit.SECONDS);
    }

    private synchronized void leak() {
        currentSize = Math.max(0, currentSize - leakRate);
    }

    public synchronized boolean addPacket(int packetSize){
        if(currentSize + packetSize <= maxSize){
            currentSize += packetSize;
            return true;
        } else {
            return false;
        }
    }
}
