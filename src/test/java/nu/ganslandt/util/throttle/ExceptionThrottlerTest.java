package nu.ganslandt.util.throttle;

public class ExceptionThrottlerTest extends ThrottleTest {
    @Override
    Throttle getThrottle(int requestsPerSecond) {
        return new ExceptionThrottler(requestsPerSecond);
    }
}
