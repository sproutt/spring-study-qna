package codesquad.net.slipp.web.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException() {
        super("{ ERROR_CODE : " + 110 + " }");
    }
}
