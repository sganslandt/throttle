package nu.ganslandt.util.throttle;

public class DelayingThrottleTest extends ThrottleTest {
    @Override
    Throttle getThrottle(int requestsPerSecond) {
        return new DelayingThrottle(requestsPerSecond);
    }
}
