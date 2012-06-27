package nu.ganslandt.util.throttle;

public class ThrottleStats {

    private long totalRequests = 0;
    private long throttledRequests = 0;

    protected void addRequest() {
        totalRequests++;
    }

    protected void addThrottledRequest() {
        throttledRequests++;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getThrottledRequest() {
        return throttledRequests;
    }
}
