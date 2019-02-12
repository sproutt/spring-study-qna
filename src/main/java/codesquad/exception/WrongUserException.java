package codesquad.exception;

public class WrongUserException extends RuntimeException {
    public WrongUserException(Long id){
        super(id+" 권한이 없습니다.");
    }
}
