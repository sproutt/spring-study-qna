package codesquad.controller;

import codesquad.domain.Question;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "qna/index";
    }

    @GetMapping("/questions/form")
    public String show() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id));
        return "qna/show";
    }
}
