package codesquad.exception;

public class CannotDeleteQuestionException extends RuntimeException {
    public CannotDeleteQuestionException(){
        super("질문을 삭제할 수 없습니다");
    }
}
