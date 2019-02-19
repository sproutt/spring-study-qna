package codesquad.controller;

import codesquad.service.AnswerService;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping
    public String create(HttpSession session, @PathVariable Long questionId, String contents) {
        if (!SessionChecker.isLoggedIn(session)) {
            return "redirect:/users/login";
        }
        answerService.create(session, questionId, contents);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(HttpSession session, @PathVariable Long questionId, @PathVariable Long id) {
        answerService.delete(session, questionId, id);
        return "redirect:/questions/{questionId}";
    }


}