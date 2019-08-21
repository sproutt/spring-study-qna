package codesquad.controller;

import codesquad.domain.Question;
import codesquad.service.QuestionService;
import codesquad.util.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class QuestionController {

  private QuestionService questionService;

  QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @GetMapping("/")
  public String showQuestionList(Model model) {

    model.addAttribute("questions", questionService.getQuestions());

    return "main/index";
  }

  @GetMapping("/questions/form")
  public String showForm(HttpSession session) {

    HttpSessionUtils.checkLogining(session);

    return "qna/form";
  }

  @PostMapping("/questions")
  public String showQuestions(Question question) {

    questionService.addQuestion(question);

    return "redirect:/";
  }

  @GetMapping("/questions/{id}")
  public String findQuestion(@PathVariable("id") Long id, Model model) {

    model.addAttribute("question", questionService.getQuestionById(id));

    return "qna/show";
  }

  @DeleteMapping("/questions/{id}")
  public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {

    HttpSessionUtils.checkLogining(session);

    questionService.checkWriterIsUserOfSession(HttpSessionUtils.getUserFromSession(session), id);
    questionService.deleteQuestion(id);

    return "redirect:/";
  }

  @PutMapping("/questions/{id}")
  public String updateQuestion(@PathVariable("id") Long id, Question newQuestion,
      HttpSession session) {

    HttpSessionUtils.checkLogining(session);

    questionService.checkWriterIsUserOfSession(HttpSessionUtils.getUserFromSession(session), id);
    questionService.updateQuestion(id, newQuestion);

    return "redirect:/";
  }

  @GetMapping("/questions/{id}/form")
  public String showQuestionInfo(@PathVariable("id") Long id, Model model, HttpSession session) {

    Question question = questionService.getQuestionById(id);
    questionService.checkWriterIsUserOfSession(HttpSessionUtils.getUserFromSession(session), id);

    model.addAttribute("question", question);

    return "qna/updateForm";
  }
}
