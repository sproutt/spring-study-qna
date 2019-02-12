package codesquad.net.slipp.web.exception;

public class UserNotFoundException extends RuntimeException {
    private static final int ERR_CODE = 100;

    public UserNotFoundException(String userId){
        super("ERR_CORD : " + ERR_CODE+", USER_ID :"+ userId );
    }
}
