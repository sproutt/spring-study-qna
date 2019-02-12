package codesquad.exception;

public class UserNotEqualException extends RuntimeException{
    public UserNotEqualException(){
        super("세션유저와 일치하지 않습니다.");
    }
}
