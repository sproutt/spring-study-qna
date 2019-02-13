package codesquad.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long id) {
        super(id + "is not available");
    }
}
