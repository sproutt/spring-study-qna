package codesquad.controller;

import codesquad.dto.Question;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

  private List<Question> questions = new ArrayList<>();

  @GetMapping("/questions")
  public String showQuestionForm(){
    return "qna/form";
  }

  @PostMapping("/questions")
  public String showQuestions(Question question) {

    questions.add(question);

    return "redirect:/";
  }

  @GetMapping("/")
  public String showQuestionList(Model model){

    model.addAttribute("questions", questions);

    return "main/index";
  }

  @GetMapping("/show")
  public String test(){
    return "qna/show";
  }

  @GetMapping("/questions/{index}")
  public String findQuestion(@PathVariable("index") String index, Model model){

    model.addAttribute("question", questions.get(Integer.parseInt(index)));

    return "qna/show";

  }
}
