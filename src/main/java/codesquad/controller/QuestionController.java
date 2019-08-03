package codesquad.controller;

import codesquad.dto.Question;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @GetMapping("/")
  public String showQuestionList(Model model){

    model.addAttribute("questions", questionService.getQuestions());

    return "main/index";
  }

  @GetMapping("/questions")
  public String showQuestionForm(){
    return "qna/form";
  }

  @PostMapping("/questions")
  public String showQuestions(Question question) {

    questionService.addQuestion(question);

    return "redirect:/";
  }

  @GetMapping("/questions/{index}")
  public String findQuestion(@PathVariable("index") String index, Model model){

    model.addAttribute("question", questionService.getQuestion(index));

    return "qna/show";

  }
}
