package codesquad.web;

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

    private List<Question> questions = new ArrayList<>();
    private static Long index = 1L;

    @PostMapping("/questions")
    public String create(Question question) {
        question.setIndex(index++);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable Long index, Model model) {
        for(Question question : questions) {
            if(question.equalsIndex(index)) {
                model.addAttribute("question", question);
                break;
            }
        }
        return "qna/show";
    }
}
