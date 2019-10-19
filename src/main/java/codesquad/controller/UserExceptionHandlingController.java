package codesquad.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandlingController {

    @ExceptionHandler(NullPointerException.class)
    public String userNullError() {
        System.out.println("user Null값에 접근");
        return "/user/userError";
    }
}
