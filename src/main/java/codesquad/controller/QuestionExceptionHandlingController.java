package codesquad.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class QuestionExceptionHandlingController {

    @ExceptionHandler(NoSuchElementException.class)
    public String questionNullError() {
        System.out.println("에러발생");
        return "redirect:/";
    }
}
