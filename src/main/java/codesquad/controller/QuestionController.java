package codesquad.controller;

import codesquad.domain.Question;
import codesquad.exception.NoSuchQuestionException;
import codesquad.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping("/questions")
    public String create(@ModelAttribute Question question) {
        question.createWrittenTime(getWrittenTime());
        questionRepository.save(question);
        return "redirect:/questions";
    }

    private LocalDateTime getWrittenTime() {
        return LocalDateTime.now();
    }

    @GetMapping("/questions")
    public String showAllQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String showQuestionDetail(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id)
                                              .orElseThrow(NoSuchQuestionException::new);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @DeleteMapping("/questions/{id}")
    public String remove(@PathVariable Long id) {
        questionRepository.delete(questionRepository.findById(id)
                                                    .orElseThrow(NoSuchQuestionException::new));
        return "redirect:/questions";
    }

}
