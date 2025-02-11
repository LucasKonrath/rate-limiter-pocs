package org.ldamaceno.pocs.rate_limiter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket {
    private final Integer maxSize;
    private final long tokensPerSecond;
    private final AtomicLong tokens;
    private final ScheduledExecutorService scheduledExecutorService;

    public TokenBucket(Integer maxSize, long tokensPerSecond) {
        this.maxSize = maxSize;
        this.tokensPerSecond = tokensPerSecond;
        this.tokens = new AtomicLong(maxSize);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(this::fillBucket, 0, 1, TimeUnit.SECONDS);
    }

    private void fillBucket() {
        long newValue = Math.min(tokensPerSecond, maxSize - tokens.get());
        tokens.addAndGet(newValue);
    }

    public boolean consume(){
        if(tokens.get() > 0){
            tokens.decrementAndGet();
            return true;
        }

        return false;
    }
}
