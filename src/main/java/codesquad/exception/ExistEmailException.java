package codesquad.exception;

public class ExistEmailException extends IllegalArgumentException {
    private static final String EXIST_EMAIL_ERROR = "이미 사용중인 사용자 이메일 주소 입니다";

    public ExistEmailException() {
        super(EXIST_EMAIL_ERROR);
    }
}
