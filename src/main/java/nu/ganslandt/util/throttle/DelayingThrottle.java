package nu.ganslandt.util.throttle;

public class DelayingThrottle extends Throttle {
    /**
     * Creates a new throttle that throws an exception if the throttle is exceeded
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
