package nu.ganslandt.util.throttle.mbean;

import nu.ganslandt.util.throttle.Throttle;

public class ThrottleMBeanImpl implements ThrottleMBean {

    private final Throttle throttle;

    public ThrottleMBeanImpl(Throttle throttle) {
        this.throttle = throttle;
    }

    @Override
    public long getTotalRequests() {
        return throttle.getStats().getTotalRequests();
    }

    @Override
    public long getThrottledRequests() {
        return throttle.getStats().getTotalRequests();
    }

    @Override
    public long getActualRequestPerSecond() {
        return 0;
    }

    @Override
    public long getRequestsPerSecond() {
        return throttle.getRequestPerSecond();
    }

    @Override
    public void setRequestsPerSecond(long requestsPerSecond) {
        throttle.setRequestPerSecond(requestsPerSecond);
    }

    @Override
    public void clearStats() {
        throttle.clearStats();
    }
}
