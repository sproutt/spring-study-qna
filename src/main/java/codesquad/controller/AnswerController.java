package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

  private final AnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @PutMapping("/{id}")
  public String updateAnswer(@PathVariable("questionId") Long questionId,
      @PathVariable("id") Long id, Answer updatedAnswer, HttpSession session) {

    HttpSessionUtils.checkLogining(session);

    answerService.updateAnswer(id, HttpSessionUtils.getUserFromSession(session), updatedAnswer);

    return "redirect:/questions/" + questionId;
  }

  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable("id") Long id, HttpSession session, Model model) {

    HttpSessionUtils.checkLogining(session);

    model.addAttribute("answers", answerService.getAllAnswers());
    model.addAttribute("editAnswer", answerService.findAnswerById(id));

    return "answer/updateForm";
  }
}
