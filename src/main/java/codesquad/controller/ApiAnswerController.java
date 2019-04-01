package codesquad.controller;

import codesquad.Result;
import codesquad.dto.AnswerDTO;
import codesquad.exception.UnAuthorizedException;
import codesquad.model.Answer;
import codesquad.service.AnswerService;
import codesquad.utils.HttpSessionUtils;
import com.sun.deploy.net.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("")
    public Answer uploadAnswer(@PathVariable Long questionId, @RequestBody AnswerDTO answerDTO, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            throw new UnAuthorizedException();
        }
        return answerService.saveAnswer(session, questionId, answerDTO.getContents());
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            throw new UnAuthorizedException();
        }

        Result result = new Result(id);

        if (!answerService.deleteAnswer(HttpSessionUtils.getSessionedUser(session), id)) {
            return result.fail("error message");
        }

        return result.ok();
    }
}
