package nu.ganslandt.util.throttle;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class Throttle {

    protected DelayQueue<Token> tokenBucket;
    private long requestPerSecond;
    private ThrottleStats stats;

    public Throttle(int requestPerSecond) {
        this.requestPerSecond = requestPerSecond;
        this.stats = new ThrottleStats();
        this.tokenBucket = new DelayQueue<Token>();
    }

    public void throttle() {
        stats.addRequest();
        if (tokenBucket.size() >= requestPerSecond) {
            stats.addThrottledRequest();
            doThrottle();
        }

        handleRequest();
    }

    public long getRequestPerSecond() {
        return requestPerSecond;
    }

    public void setRequestPerSecond(long requestPerSecond) {
        this.requestPerSecond = requestPerSecond;
    }

    public void clearStats() {
        this.stats = new ThrottleStats();
    }

    public ThrottleStats getStats() {
        return stats;
    }

    protected abstract void doThrottle();

    private void handleRequest() {
        tokenBucket.add(new Token(1 /* seconds from now*/));
    }

    private class Token implements Delayed {

        private long expiryTimeMillis;

        public Token(int i) {
            expiryTimeMillis = System.currentTimeMillis() + i * 1000;
        }

        public long getDelay(TimeUnit timeUnit) {
            switch (timeUnit) {
                case NANOSECONDS:
                    return (expiryTimeMillis - System.currentTimeMillis()) * 1000;
                default:
                    throw new RuntimeException("Don't know how to handle TimeUnit " + timeUnit);
            }
        }

        public int compareTo(Delayed that) {
            if (this.getDelay(TimeUnit.NANOSECONDS) == that.getDelay(TimeUnit.NANOSECONDS))
                return 0;

            if (this.getDelay(TimeUnit.NANOSECONDS) > that.getDelay(TimeUnit.NANOSECONDS))
                return 1;

            return -1;
        }
    }
}
