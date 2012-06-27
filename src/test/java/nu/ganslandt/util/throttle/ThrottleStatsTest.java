package nu.ganslandt.util.throttle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThrottleStatsTest {

    private DelayingThrottle throttle;

    @Before
    public void setup() {
        throttle = new DelayingThrottle(10);
    }

    @Test
    public void testGetTotalRequest_beforeAnyRequests_returnsZero() {
        assertEquals(0, throttle.getStats().getTotalRequests());
    }

    @Test
    public void testGetTotalRequests_after1Request_returns1() {
        throttle.throttle();
        assertEquals(1, throttle.getStats().getTotalRequests());
    }

    @Test
    public void testGetTotalRequest_after11Requests_returns11() {
        for (int i = 0; i != 11; i++)
            throttle.throttle();
        assertEquals(11, throttle.getStats().getTotalRequests());
    }

    @Test
    public void testGetTotalRequest_afterClear_returns0() {
        throttle.throttle();
        throttle.clearStats();
        assertEquals(0, throttle.getStats().getTotalRequests());
    }

    @Test
    public void testGetRequestsPerSecond_after1Request_returnsSligthlyLessThanOne() {
        // TODO Think about
    }

    @Test
    public void testGetRequestsPerSecond_after1RequestAndASecondPasses_returnsSlightlyLessThanTwo() {
        // TODO Think about
    }

    @Test
    public void testGetThrottledRequest_beforeThrottleLimitExceeds_returns0() {
        throttle.throttle();
        assertEquals(0, throttle.getStats().getThrottledRequest());
    }

    @Test
    public void testGetThrottledRequest_afterThrottleLimitExceed_returns1() {
        for (int i = 0; i != 10; i++)
            throttle.throttle();

        throttle.throttle();
        assertEquals(1, throttle.getStats().getThrottledRequest());
    }

    @Test
    public void testGetThrottledRequest_afterThrottleLimitExceededAndTimePasses_returnsSame() throws InterruptedException {
        for (int i = 0; i != 10; i++)
            throttle.throttle();

        throttle.throttle();
        Thread.sleep(2000);

        assertEquals(1, throttle.getStats().getThrottledRequest());
    }


}
