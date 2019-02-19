package codesquad.controller;

import codesquad.model.Question;
import codesquad.model.User;
import codesquad.service.QuestionService;
import codesquad.service.UserService;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/form")
    public String questionForm(HttpSession session, Model model) {
        if (!SessionChecker.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("user", SessionChecker.loggedinUser(session));
        return "qna/form";
    }

    @PostMapping
    public String quest(Question question, HttpSession session) {
        User writer = SessionChecker.loggedinUser(session);
        questionService.update(question, writer);
        return "redirect:/questions";
    }

    @GetMapping
    public String bringQuestionsList(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "qna/list";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("/{id}/updateForm")
    public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionedUser = SessionChecker.loggedinUser(session);
        model.addAttribute("question", questionService.getUpdatedQuestion(sessionedUser, id));
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question question, HttpSession session) {
        User sessionedUser = SessionChecker.loggedinUser(session);
        questionService.update(question, sessionedUser);
        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        User sessionedUser = SessionChecker.loggedinUser(session);
        questionService.delete(sessionedUser, id);
        return "redirect:/questions";
    }
}
