package codesquad.exception;

import java.util.NoSuchElementException;

public class InvalidPasswordException extends NoSuchElementException {
    public InvalidPasswordException() {
        super("유효한 비밀번호가 아닙니다.");
    }
}
