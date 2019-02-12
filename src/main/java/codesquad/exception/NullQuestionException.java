package codesquad.exception;

public class NullQuestionException extends RuntimeException {

    public NullQuestionException(Long id) {
        super(id + " 잘못된 질문 접근");
    }

}
