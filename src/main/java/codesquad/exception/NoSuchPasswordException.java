package codesquad.exception;

import java.util.NoSuchElementException;

public class NoSuchPasswordException extends NoSuchElementException {
    private static final String NOT_MATCH_PASSWORD_ERROR = "해당 사용자 아이디와 일치하는 비밀번호가 아닙니다.";

    public NoSuchPasswordException() {
        super(NOT_MATCH_PASSWORD_ERROR);
    }
}
