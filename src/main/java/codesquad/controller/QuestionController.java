package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @PostMapping("/questions")
    public String post(Question question) {
        question.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String update(@PathVariable Long id, Question modifiedQuestion) {
        modifiedQuestion.setId(id);
        modifiedQuestion.setCreatedDate(questionRepository.findById(id).get().getCreatedDate());
        questionRepository.save(modifiedQuestion);
        return "redirect:/";
    }
}
