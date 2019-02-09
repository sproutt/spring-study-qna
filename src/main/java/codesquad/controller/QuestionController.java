package codesquad.controller;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.utils.HttpSessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String questionForm(HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        return "qna/form";
    }

    @PostMapping("")
    public String post(Question question, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/invalid";
        }
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        question.setWriter(user);
        question.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        if (!user.match(id)) {
            return "qna/update_deny";
        }

        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question modifiedQuestion, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        if (!user.match(id)) {
            return "qna/update_deny";
        }

        Question question = questionRepository.findById(id).get();
        question.update(modifiedQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        if (!user.match(id)) {
            return "qna/update_deny";
        }

        questionRepository.delete(questionRepository.findById(id).get());
        return "redirect:/";
    }
}
