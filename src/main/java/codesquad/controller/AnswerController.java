package codesquad.controller;

import codesquad.exception.UserNotEqualException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.service.AnswerService;
import codesquad.service.QuestionService;
import codesquad.service.UserService;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    QuestionService questionService;

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
    public String delete(HttpSession session, @PathVariable Long id) {
        answerService.delete(session, id);
        return "redirect:/questions/{questionId}";
    }


}
