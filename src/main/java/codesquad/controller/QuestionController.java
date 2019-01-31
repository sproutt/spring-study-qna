package codesquad.controller;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Controller
public class QuestionController {

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
        Question question = questionRepository.findById(id).get();
        question.update(modifiedQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.delete(questionRepository.findById(id).get());
        return "redirect:/";
    }
}
