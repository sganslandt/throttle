package nu.ganslandt.util.throttle;

public class ThrottleException extends RuntimeException {
    public ThrottleException(String message) {
        super(message);
    }

    public ThrottleException(Throwable e) {
        super(e);
    }
}
