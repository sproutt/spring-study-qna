package codesquad.exception;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException() {
        super("해당 Answer을 찾을 수 없습니다");
    }
}
