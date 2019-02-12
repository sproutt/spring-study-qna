package codesquad.exception;

public class UserNotLoginException extends RuntimeException {
    public UserNotLoginException(){
        super("로그인되어있지 않습니다.");
    }
}
