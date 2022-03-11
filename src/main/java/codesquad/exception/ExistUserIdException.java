package codesquad.exception;

public class ExistUserIdException extends IllegalArgumentException {
    private static final String EXIST_USER_ID_ERROR = "이미 사용중인 사용자 ID 입니다";

    public ExistUserIdException() {
        super(EXIST_USER_ID_ERROR);
    }
}
