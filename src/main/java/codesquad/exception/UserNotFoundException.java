package codesquad.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super(userId + "is not available");
    }

    public UserNotFoundException(Long id) {
        super(id + "is not available");
    }
}
