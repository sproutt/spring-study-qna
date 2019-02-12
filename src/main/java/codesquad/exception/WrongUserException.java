package codesquad.exception;

public class WrongUserException extends RuntimeException {

    public WrongUserException(String userId){
        super(userId+"는 잘못된 유저입니다.");
    }

    public WrongUserException(Long id){
        super("잘못된 유저 접근");
    }

}
