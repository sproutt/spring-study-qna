package codesquad.controller;

import codesquad.service.AnswerService;
import codesquad.service.QuestionService;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public String uploadAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }
        answerService.saveAnswer(HttpSessionUtils.getSessionedUser(session), questionService.findById(questionId), contents);
        return "redirect:/questions/" + questionId;
    }

    @PutMapping("/{id}")
    public String modify(@PathVariable String questionId, @PathVariable Long id) {
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }

        if (!answerService.isSameWriter(answerService.findWriterByAnswerId(id), HttpSessionUtils.getSessionedUser(session))) {
            return "redirect:/questions/" + questionId;
        }

        answerService.deleteAnswer(id);

        return "redirect:/questions/" + questionId;
    }
}
