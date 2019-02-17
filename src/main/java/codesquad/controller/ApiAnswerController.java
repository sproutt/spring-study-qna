package codesquad.controller;

import codesquad.dto.AnswerDto;
import codesquad.exception.UserNotLoginException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.service.AnswerService;
import codesquad.service.QuestionService;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public String create(@PathVariable Long questionId, AnswerDto answerDto, HttpSession session) {
        if (!SessionChecker.isLoggedIn(session)) {
            throw new UserNotLoginException();
        }
        User writer = SessionChecker.loggedinUser(session);
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer(question, writer, answerDto.getContents());
        question.addAnswer(answer);
        answerService.save(answer);
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session){
        if(SessionChecker.isLoggedIn(session)){
            throw new UserNotLoginException();
        }
        answerService.delete(session, questionId, id);
        return "redirect:/questions/{questionId}";
    }
}
