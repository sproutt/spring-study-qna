package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
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
    public String replyAnswer(@PathVariable("questionId") Long id, Answer answer, HttpSession session) {
        return answerService.reply(id, answer, session);
    }

    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session) {
        return answerService.delete(questionId, id, session);
    }
}
