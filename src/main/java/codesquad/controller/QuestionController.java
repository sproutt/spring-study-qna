package codesquad.controller;

import codesquad.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();
    private Long index = 0L;

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/questions")
    public String question(Question question) {
        question.setIndex(++index);
        question.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable Long index, Model model) {
        for (Question question : questions) {
            if (question.getIndex() == index) {
                model.addAttribute("question", question);
            }
        }
        return "qna/show";
    }
}
