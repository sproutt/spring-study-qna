package codesquad.web;

import codesquad.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/";
    }
}
