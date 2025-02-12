package org.ldamaceno.pocs.rate_limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FixedWindowRateLimiter {
    private final long rateLimit;
    private final long windowTimeMs;
    private final ConcurrentMap<String, Window> windows;

    public FixedWindowRateLimiter(long rateLimit, long windowTimeMs){
        this.rateLimit = rateLimit;
        this.windowTimeMs = windowTimeMs;

        this.windows = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String id){
        long now = System.currentTimeMillis();
        Window window = windows.get(id);

        if(window == null){
            window = new Window(now, 1);
            Window previousWindow = windows.putIfAbsent(id, window);
            if(previousWindow != null){
                window = previousWindow;
            }
        }

        if(now - window.getStartTime() > windowTimeMs){
            window.reset(now, 1);
            windows.put(id, window);
            return true;
        } else if (window.getCount() < rateLimit) {
            window.incrementCount();
            return true;
        }

        return false;
    }

    private static class Window {
        private long startTime;
        private int count;

        public Window(long startTime, int count) {
            this.startTime = startTime;
            this.count = count;
        }

        public void incrementCount() {
            count++;
        }

        public void reset(long startTime, int count){
            this.startTime = startTime;
            this.count = count;
        }

        public long getStartTime(){
            return this.startTime;
        }

        public long getCount(){
            return this.count;
        }
    }
}
