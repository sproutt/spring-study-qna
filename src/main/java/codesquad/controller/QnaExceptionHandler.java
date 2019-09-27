package codesquad.controller;

import codesquad.dto.Result;
import codesquad.exception.LoginException;
import codesquad.exception.LoginFailException;
import codesquad.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class QnaExceptionHandler {

  @ExceptionHandler(value = LoginException.class)
  public String loginException(LoginException e) {
    e.printStackTrace();

    return "redirect:/users/login/form";
  }

  @ExceptionHandler(value = LoginFailException.class)
  public String loginFailException(LoginException e) {
    e.printStackTrace();

    return "user/login_failed";
  }

  @ExceptionHandler(IllegalStateException.class)
  public ModelAndView exception(Exception e) {

    ModelAndView modelAndView = new ModelAndView("exception");
    modelAndView.addObject("message", e.getMessage());

    return modelAndView;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ResponseException.class)
  public Result<Object> responseError(ResponseException e) {
    return Result.fail(e.getMessage());
  }
}
