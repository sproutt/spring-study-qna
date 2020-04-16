package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.QuestionRepository;
import codesquad.service.QuestionService;
import codesquad.service.UserService;
import codesquad.util.HttpSessionUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("questionList", questionService.findQuestions());
        return "qna/index";
    }

    @GetMapping("/questions/form")
    public String show(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(String title, String contents) {
        questionService.create(title, contents);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        if (!userService.isSessionedUser()) {
            throw new IllegalStateException("You can't edit other's article");
        }
        if (!questionService.checkUserId(id)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/updateForm";
    }

    @PostMapping("/questions/{id}/update")
    public String update(@PathVariable Long id, String title, String contents) {
        if (!userService.isSessionedUser()) {
            throw new IllegalStateException("You can't edit other's article");
        }
        if (!questionService.checkUserId(id)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        questionService.updateQuestionInfo(id, title, contents);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Long id) {
        if (!userService.isSessionedUser()) {
            return "redirect:/users/loginForm";
        }
        if (!questionService.checkUserId(id)) {
            return "redirect:/users/loginForm";
        }
        questionService.deleteQuestion(id);
        return "redirect:/";
    }

}
