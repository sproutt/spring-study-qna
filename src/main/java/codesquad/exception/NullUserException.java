package codesquad.exception;

public class NullUserException extends RuntimeException {

    public NullUserException(String userId) {
        super(userId + "는 잘못된 유저입니다.");
    }

    public NullUserException(Long id) {
        super(id + " 잘못된 유저 접근");
    }

}
