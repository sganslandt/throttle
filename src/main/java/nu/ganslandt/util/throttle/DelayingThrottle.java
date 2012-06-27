package nu.ganslandt.util.throttle;

public class DelayingThrottle extends Throttle {

    /**
     * Creates a new throttle that delays all requests that would make exceed requestPerSecond
     */
    public DelayingThrottle(int requestPerSecond) {
        super(requestPerSecond);
    }

    protected void doThrottle() {
        try {
            tokenBucket.take();
        } catch (InterruptedException e) { /* log */ }
    }
}
