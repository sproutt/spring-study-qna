package codesquad.controller;

import codesquad.dto.AnswerDTO;
import codesquad.exception.UserNotLoginException;
import codesquad.common.RestResponse;
import codesquad.service.AnswerService;
import codesquad.service.ApiAnswerService;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public RestResponse create(@PathVariable Long questionId, AnswerDTO answerDTO, HttpSession session) {
        answerService.create(session, questionId, answerDTO);
        return RestResponse.success(questionId);
    }

    @DeleteMapping("/{id}")
    public RestResponse delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (SessionChecker.isLoggedIn(session)) {
            throw new UserNotLoginException();
        }
        if (!answerService.delete(session, questionId, id)) {
            return RestResponse.fail(questionId, id);
        }
        return RestResponse.ok(questionId, id);
    }
}
