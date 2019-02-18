package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /*
    @PostMapping("")
    public String register(@PathVariable Long questionId, Answer answer, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }

        answerService.register(questionId, answer, httpSession);
        return "redirect:/questions/" + questionId;
    }
*/
    @DeleteMapping("")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLogin(httpSession)) {
            return "users/login";
        }
        if (!answerService.match(id, httpSession)) {
            return "qna/deny";
        }

        answerService.delete(questionId, id);
        return "redirect:/questions/" + questionId;
    }
}
