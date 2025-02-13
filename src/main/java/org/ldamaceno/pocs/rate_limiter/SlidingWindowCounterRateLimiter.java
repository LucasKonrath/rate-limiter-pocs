package org.ldamaceno.pocs.rate_limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class SlidingWindowCounterRateLimiter {
    private final int maxRequests;
    private final long windowTimeMs;
    private final ConcurrentMap<String, Window> windows;

    public SlidingWindowCounterRateLimiter(int maxRequests, long windowTimeMs){
        this.maxRequests = maxRequests;
        this.windowTimeMs = windowTimeMs;
        this.windows = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        Window window = windows.get(key);
        if (window == null) {
            window = new Window(maxRequests, windowTimeMs);
            Window previousWindow = windows.putIfAbsent(key, window);
            if (previousWindow != null) {
                window = previousWindow;
            }
        }

        return window.isAllowed(now);
    }

    private static class Window {
        private final int maxRequests;
        private final long windowTimeMs;
        private final AtomicLong count;
        private long startTime;

        public Window(int maxRequests, long windowTimeMs) {
            this.maxRequests = maxRequests;
            this.windowTimeMs = windowTimeMs;
            this.count = new AtomicLong(0);
            this.startTime = System.currentTimeMillis();
        }

        public boolean isAllowed(long now) {

            if(now - startTime  > windowTimeMs){
                count.set(0);
                startTime = now;
            }

            if(count.get() < maxRequests){
                return count.compareAndSet(count.get(), count.get() + 1);
            }

            return false;
        }

    }

}
