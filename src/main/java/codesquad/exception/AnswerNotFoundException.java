package codesquad.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super(id + "가 존재하지 않습니다");
    }
}
