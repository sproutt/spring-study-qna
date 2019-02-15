package codesquad.controller;

import codesquad.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    public List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String questions(Question question) {
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "/qna/list";
    }

    @GetMapping("/questions/{index}")
    public String accessQuestion(@PathVariable int index, Model model) {
        model.addAttribute("question", questions.get(index));
        return "/qna/show";
    }
}
