package codesquad.net.slipp.web.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("{ ERROR_CODE : " + 100 + ", PK : " + id + " }");
    }
    public UserNotFoundException(String userId) {
        super("{ ERROR_CODE : " + 101 + ", USER_ID : " + userId + " }");
    }
}
