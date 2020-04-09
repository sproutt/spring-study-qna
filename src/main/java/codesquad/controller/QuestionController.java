package codesquad.controller;

import codesquad.domain.Question;
import codesquad.repository.QuestionRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/updateForm";
    }

    @PostMapping("/questions/{id}/update")
    public String update(@PathVariable Long id, Question question) {
        Question beforeQuestion = questionRepository.findById(id).get();
        beforeQuestion.changeQuestionInfo(question);
        questionRepository.save(beforeQuestion);
        return "redirect:/";
    }
}
