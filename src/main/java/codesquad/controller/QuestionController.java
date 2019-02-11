package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.service.QuestionService;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

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
            return "users/login";
        }
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        question.setWriter(user);
        question.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        questionService.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
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

        model.addAttribute("question", questionService.findById(id));
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

        questionService.updateById(id, modifiedQuestion);
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

        questionService.deleteById(id);
        return "redirect:/";
    }
}
