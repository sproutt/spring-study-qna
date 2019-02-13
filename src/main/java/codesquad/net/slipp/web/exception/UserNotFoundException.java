package codesquad.net.slipp.web.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("ERROR : "  + id + "번째 유저가 존재하지 않습니다. ");
    }

    public UserNotFoundException(String userId) {
        super("ERROR : " + userId + "라는 유저가 존재하지 않습니다." );
    }
}
