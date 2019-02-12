package codesquad.net.slipp.web.exception;

import codesquad.net.slipp.web.domain.User;

import javax.servlet.http.HttpSession;

public class SessionNotMatchException extends RuntimeException {

    public SessionNotMatchException() {
        super("{ " + "ERROR_CODE : " + 111 + " }");
    }
}
