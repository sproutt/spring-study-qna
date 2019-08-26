package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

  private AnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @PostMapping
  public String createAnswer(@PathVariable("questionId") Long id, Answer answer,
      HttpSession session) {
    System.out.println("dhlfhdls");
    HttpSessionUtils.checkLogining(session);

    System.out.println("hello"+answer.toString());
    answerService.addAnswer(answer);

    return "redirect:/questions/" + id;
  }
}
