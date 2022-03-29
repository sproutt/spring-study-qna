package codesquad.controller;

import codesquad.domain.Question;
import codesquad.exception.NoSuchQuestionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private final List<Question> questions = new ArrayList<>();
    private final Long id = 1L;

    @PostMapping("/questions")
    public String create(@ModelAttribute Question question) {
        question.createId(id);
        question.createWrittenTime(getWrittenTime());
        questions.add(question);
        return "redirect:/questions";
    }

    private String getWrittenTime() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

        return nowDateTime.format(formatter);
    }

    @GetMapping("/questions")
    public String showAllQuestions(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String showQuestionInfo(@PathVariable int index, Model model) {
        Question question = questions.stream()
                                     .filter(ques -> ques.equalsIndex(index))
                                     .findAny()
                                     .orElseThrow(NoSuchQuestionException::new);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
