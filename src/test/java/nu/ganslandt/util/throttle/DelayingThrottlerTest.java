package nu.ganslandt.util.throttle;

public class DelayingThrottlerTest extends ThrottleTest {
    @Override
    Throttle getThrottle(int requestsPerSecond) {
        return new DelayingThrottler(requestsPerSecond);
    }
}
