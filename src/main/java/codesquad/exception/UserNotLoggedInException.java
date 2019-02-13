package codesquad.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super("user not logged-in");
    }
}
