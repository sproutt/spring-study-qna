package codesquad.controller;

import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.service.QuestionService;
import codesquad.service.UserService;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @PostMapping
    public String create(HttpSession session, @PathVariable Long questionId, String contents) {
        if (!SessionChecker.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }
        questionService.addAnswer(questionId, session, contents);
        return "redirect:questions/{questionsId}";
    }

}
