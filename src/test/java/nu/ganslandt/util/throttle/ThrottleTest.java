package nu.ganslandt.util.throttle;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public abstract class ThrottleTest {

    @Test
    public void test_ASingleRequest_Succeeds() {
        Throttle throttle = getThrottle(1);

        throttle.throttle();
        // No exception should be thrown
    }

    @Test
    public void test_RequestDuringFiveSeconds_GetsApproximatelyCorrectPassCount() {
        int requestsPerSecond = 100;
        int seconds = 5;
        Throttle throttle = getThrottle(requestsPerSecond);

        int passCount = countPassesFor(seconds, throttle);

        assertTrue("More than 10% less requests than expected passed ("+passCount+").", requestsPerSecond * seconds * .9 < passCount);
        assertTrue("More than 10^ more requests than expected passed ("+passCount+").", passCount < requestsPerSecond * seconds * 1.1);
    }

    @Test
    public void test_BurstAfterSilentTime_NoBufferFromSilence() throws InterruptedException {
        int requestsPerSecond = 100;
        int seconds = 3;
        Throttle throttle = getThrottle(requestsPerSecond);

        Thread.sleep(2000);
        int passCount = countPassesFor(seconds, throttle);

        assertTrue("More than 10% less requests than expected passed ("+passCount+").", requestsPerSecond * seconds * .9 < passCount);
        assertTrue("More than 10% more requests than expected passed ("+passCount+").", passCount < requestsPerSecond * seconds * 1.1);
    }

    private int countPassesFor(int seconds, Throttle throttle) {
        int passCount = 0;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < seconds * 1000) {
            try {
                throttle.throttle();
                passCount++;
            } catch (ThrottleException e) {
                // nope, didn't pass and shouldn't be counted
            }
        }
        return passCount;
    }

    abstract Throttle getThrottle(int requestsPerSecond);
}
