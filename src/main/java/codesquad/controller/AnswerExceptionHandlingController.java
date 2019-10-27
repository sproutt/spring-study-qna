package codesquad.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AnswerExceptionHandlingController {

    @ExceptionHandler(NullPointerException.class)
    public String answerNullError() {
        System.out.println("answer Null값에 접근");
        return "/qna/questionError";
    }
}
