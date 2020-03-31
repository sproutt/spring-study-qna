package codesquad.controller;

import codesquad.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questionList = new ArrayList<>();

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("questionList", questionList);
        return "qna/index";
    }

    @GetMapping("/questions/form")
    public String show() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        questionList.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String get(@PathVariable int index, Model model) {
        int questionIndex = index - 1;
        model.addAttribute("question", questionList.get(questionIndex));
        return "qna/show";
    }
}
