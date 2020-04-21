package codesquad.controller;

import codesquad.service.QuestionService;
import codesquad.service.UserService;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

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
    public String create(String title, String contents, HttpSession session) {
        questionService.create(title, contents, session);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.isSessionedUser(session)) {
                throw new IllegalStateException("You can't edit other's article");
        }
        if (!questionService.checkUserId(id, session)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/updateForm";
    }

    @PostMapping("/questions/{id}/update")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
        if (!userService.isSessionedUser(session)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        if (!questionService.checkUserId(id, session)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        questionService.updateQuestionInfo(id, title, contents);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!userService.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }
        if (!questionService.checkUserId(id, session)) {
            return "redirect:/users/loginForm";
        }
        questionService.deleteQuestion(id);
        return "redirect:/";
    }

}
