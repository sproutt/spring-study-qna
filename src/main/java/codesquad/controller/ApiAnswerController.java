package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session) {
        return answerService.register(questionId, answer, session);
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession httpSession) {
        return answerService.delete(questionId, id, httpSession);
    }
}