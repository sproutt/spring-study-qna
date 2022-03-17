package codesquad.exception;

import java.util.NoSuchElementException;

public class NoSuchQuestionException extends NoSuchElementException {
    private static final String NOT_EXIST_QUESTION_ID_ERROR = "해당하는 질문을 찾을 수 없습니다.";

    public NoSuchQuestionException() {
        super(NOT_EXIST_QUESTION_ID_ERROR);
    }
}
