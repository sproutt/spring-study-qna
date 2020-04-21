package codesquad.controller;

import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/questions/{questionId}/answers")
    public String create(@PathVariable Long questionId, String answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        answerService.create(questionId, answer, session);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        answerService.delete(id, session);
        return "redirect:/questions/" + questionId;
    }

}
