package codesquad.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long id) {
        super(id + "가 존재하지 않습니다");
    }
}
