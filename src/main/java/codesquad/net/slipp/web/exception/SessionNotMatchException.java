package codesquad.net.slipp.web.exception;

import codesquad.net.slipp.web.domain.User;

import javax.servlet.http.HttpSession;

public class SessionNotMatchException extends RuntimeException {

    public SessionNotMatchException() {
        super("ERROR : 세션 권한이 없습니다.");
    }
}
