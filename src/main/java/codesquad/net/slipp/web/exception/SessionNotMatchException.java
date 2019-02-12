package codesquad.net.slipp.web.exception;

import codesquad.net.slipp.web.domain.User;

import javax.servlet.http.HttpSession;

public class SessionNotMatchException extends RuntimeException{
    private static final int ERROR_CODE = 111;

    public SessionNotMatchException(){
        super("{ " +"ERROR_CODE : "+ ERROR_CODE+ " }");
    }
}
