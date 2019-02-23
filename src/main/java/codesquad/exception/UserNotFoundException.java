package codesquad.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("해당 User가 존재하지 않습니다.");
    }
}
