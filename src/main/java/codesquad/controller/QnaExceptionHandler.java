package codesquad.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class QnaExceptionHandler {

  @ExceptionHandler(value = LoginException.class)
  public String loginException(LoginException e) {
    e.printStackTrace();

    return "redirect:/users/login/form";
  }

  @ExceptionHandler(IllegalStateException.class)
  public ModelAndView exception(Exception e) {

    ModelAndView modelAndView = new ModelAndView("exception");
    modelAndView.addObject("message", e.getMessage());

    return modelAndView;
  }
}
