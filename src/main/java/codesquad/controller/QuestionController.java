package codesquad.controller;

import codesquad.domain.Question;
import codesquad.service.QuestionService;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        questionService.save(question, httpSession);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        model.addAttribute("count", questionService.getAnswerCount(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        if (!questionService.match(id, httpSession)) {
            return "qna/deny";
        }

        model.addAttribute("question", questionService.findById(id));
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question modifiedQuestion, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        if (!questionService.match(id, httpSession)) {
            return "qna/deny";
        }

        questionService.update(id, modifiedQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        if (!questionService.match(id, httpSession)) {
            return "qna/deny";
        }

        questionService.delete(id);
        return "redirect:/";
    }
}
