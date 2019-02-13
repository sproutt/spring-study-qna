package codesquad.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super(id + "is not available");
    }
}
