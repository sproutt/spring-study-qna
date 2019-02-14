package codesquad.net.slipp.web.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException() {
        super(" ERROR : 로그인된 유저가 존재하지 않습니다.");
    }
}
