package codesquad.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(){
        super("해당 유저가 존재하지 않습니다.");
    }
}
