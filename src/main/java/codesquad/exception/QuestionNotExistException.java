package codesquad.exception;

public class QuestionNotExistException extends RuntimeException {
    public QuestionNotExistException(){
        super("Question이 존재하지 않습니다.");
    }
}
