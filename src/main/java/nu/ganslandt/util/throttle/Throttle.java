package nu.ganslandt.util.throttle;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class Throttle {

    protected DelayQueue<Token> tokenBucket;
    private final int requestPerSecond;

    public Throttle(int requestPerSecond) {
        this.requestPerSecond = requestPerSecond;
        this.tokenBucket = new DelayQueue<Token>();
    }

    public void throttle() {
        if (tokenBucket.size() >= requestPerSecond)
            doThrottle();

        handleRequest();
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
