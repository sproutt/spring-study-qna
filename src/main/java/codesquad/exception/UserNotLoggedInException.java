package codesquad.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super("유저가 로그인 돼 있지 않습니다");
    }
}
