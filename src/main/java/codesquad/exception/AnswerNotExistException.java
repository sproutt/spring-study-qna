package codesquad.exception;

public class AnswerNotExistException extends RuntimeException {
    public AnswerNotExistException() {
        super("Answer Not Exist");
    }
}
