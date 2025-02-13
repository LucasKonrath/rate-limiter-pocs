package org.ldamaceno.pocs.rate_limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class SlidingWindowRateLimiter {
    private final int maxRequests;
    private final long windowTimeMs;
    private final ConcurrentMap<String, Window> windows;

    public SlidingWindowRateLimiter(int maxRequests, long windowTimeMs){
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
        private final ConcurrentLinkedQueue<Long> timestamps;

        public Window(int maxRequests, long windowTimeMs) {
            this.maxRequests = maxRequests;
            this.windowTimeMs = windowTimeMs;
            this.timestamps = new ConcurrentLinkedQueue<>();
        }

        public boolean isAllowed(long now) {
            // remove older timestamps
            while (!timestamps.isEmpty() && now - timestamps.peek() > windowTimeMs) {
                timestamps.poll();
            }

            if (timestamps.size() < maxRequests) {
                timestamps.offer(now);
                return true;
            }

            return false;
        }

    }

}
