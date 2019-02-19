package codesquad.controller;

import codesquad.model.Question;
import codesquad.repository.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String questions(Question question) {
        question.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/qna/list";
    }

    @GetMapping("questions/form")
    public String getQuestionForm(HttpSession session, Model model) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "/users/loginForm";
        }

        model.addAttribute("user", HttpSessionUtils.getSessionedUser(session));

        return "/qna/form";
    }

    @GetMapping("/questions/{id}")
    public String accessQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String modifyQuestion(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.checkSessionUser(id, session)) {
            return "/users/loginForm";
        }

        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/updateForm";
    }



    @PutMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable Long id, Question updatedQuestion) {
        Question question = questionRepository.findById(id).get();
        question.update(updatedQuestion);
        question.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        questionRepository.save(question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.checkSessionUser(id, session)) {
            return "/users/loginForm";
        }

        questionRepository.delete(questionRepository.findById(id).get());
        return "redirect:/";
    }
}
