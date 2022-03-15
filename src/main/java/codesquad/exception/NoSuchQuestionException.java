package codesquad.exception;

import java.util.NoSuchElementException;

public class NoSuchQuestionException extends NoSuchElementException {
    private static final String NOT_EXIST_QUESTION_ID_ERROR = "해당 사용자 아이디와 일치하는 사용자가 없습니다.";

    public NoSuchQuestionException() {
        super(NOT_EXIST_QUESTION_ID_ERROR);
    }
}
