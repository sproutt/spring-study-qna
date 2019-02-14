package codesquad.controller;

import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
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
    UserService userService;

    @Autowired
    AnswerRepository answerRepository;
    @PostMapping
    public String create(HttpSession session, @PathVariable Long questionId, String contents) {
        if (!SessionChecker.isLoggedIn(session)) {
            return "redirect:/users/login";
        }
        Question question = questionService.getQuestionById(questionId);
        User user = SessionChecker.loggedinUser(session);

        answerRepository.save(new Answer(question, user, contents));
        return String.format("redirect:/questions/%d", questionId);
    }
    @DeleteMapping("/{id}")
    public String delete(HttpSession session, @PathVariable Long questionId){
        User loginedUser = SessionChecker.loggedinUser(session);
        Answer answer =
        if(loginedUser.isSameUser())
    }



}
