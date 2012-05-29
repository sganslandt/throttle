package nu.ganslandt.util.throttle;

public class ExceptionThrottler extends Throttle {

    /**
     * Creates a new throttle that throws an exception if the throttle is exceeded
     */
    public ExceptionThrottler(int requestPerSecond) {
        super(requestPerSecond);
    }

    @Override
    public void throttle() {
        if (tokenBucket.poll() != null)
            try {
                tokenBucket.take();
            } catch (InterruptedException e) { /* TODO log */}

        super.throttle();
    }

    @Override
    protected void doThrottle() {
        throw new ThrottleException("Slow down, you're moving to fast.");
    }
}
