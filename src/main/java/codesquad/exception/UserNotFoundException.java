package codesquad.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super(userId + "가 존재하지 않습니다");
    }

    public UserNotFoundException(Long id) {
        super(id + "가 존재하지 않습니다");
    }
}
