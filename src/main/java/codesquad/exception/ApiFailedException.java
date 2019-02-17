package codesquad.exception;

public class ApiFailedException extends RuntimeException {
    public ApiFailedException(String message) {
        super(message);
    }
}
