package nu.ganslandt.util.throttle.mbean;

public interface ThrottleMBean {
    long getTotalRequests();
    long getThrottledRequests();
    long getActualRequestPerSecond();

    long getRequestsPerSecond();
    void setRequestsPerSecond(long requestsPerSecond);

    void clearStats();
}
