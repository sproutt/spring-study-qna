package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import codesquad.utils.HttpSessionUtils;
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
        if (!HttpSessionUtils.isLogin(session)) {
            return null;
        }

        return answerService.register(questionId, answer, session);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession) && !answerService.match(id, httpSession)) {
            return null;
        }

        answerService.delete(questionId, id);
        return "{\"answerId\":\"" + id +"\"}";
    }
}