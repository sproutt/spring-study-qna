package codesquad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {
    @GetMapping("/question/post")
    public String getQuestionForm() {
        return "qna/form";
    }
}
