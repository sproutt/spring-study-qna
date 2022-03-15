package codesquad.qua;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private final List<Question> registry = new ArrayList<>();

    @PostMapping("/questions")
    public String ask(Question question) {
        registry.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("question", registry);
        return "qna/list";
    }

    @GetMapping("/questions/{index}")
    public String qnaList(Model model, @PathVariable int index) {
        Question question = registry.get(index - 1);
        model.addAttribute("qna", question);

        return "qna/show";
    }
}
