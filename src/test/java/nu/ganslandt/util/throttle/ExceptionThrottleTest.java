package nu.ganslandt.util.throttle;

public class ExceptionThrottleTest extends ThrottleTest {
    @Override
    Throttle getThrottle(int requestsPerSecond) {
        return new ExceptionThrottle(requestsPerSecond);
    }
}
