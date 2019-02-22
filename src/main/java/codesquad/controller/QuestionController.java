package codesquad.controller;

import codesquad.model.Question;
import codesquad.service.QuestionService;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/questions")
    public String questions(Question question, HttpSession session) {
        questionService.saveQuestion(question, session);

        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionService.findQuestions());

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
        model.addAttribute("question", questionService.findById(id));

        return "/qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String modifyQuestion(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.checkSessionUser(id, session)) {
            return "/users/loginForm";
        }
        model.addAttribute("question", questionService.findById(id));

        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable Long id, Question updatedQuestion) {
        questionService.updateQuestion(questionService.findById(id), updatedQuestion);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.checkSessionUser(id, session)) {
            return "/users/loginForm";
        }
        questionService.deleteQuestion(id);

        return "redirect:/";
    }
}
