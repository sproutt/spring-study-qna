package codesquad.net.slipp.web.exception;

public class SessionNotFoundException extends RuntimeException{
    private static final int ERROR_CODE = 110;

    public SessionNotFoundException(){
        super("{ ERROR_CODE : "+ ERROR_CODE+ " }");
    }
}
