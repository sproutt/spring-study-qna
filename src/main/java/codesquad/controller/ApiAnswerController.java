package codesquad.controller;

import codesquad.dto.AnswerDTO;
import codesquad.model.Answer;
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
    public Answer uploadAnswer(@PathVariable Long questionId, @RequestBody AnswerDTO answerDTO, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return null;
        }
        return answerService.saveAnswer(session, questionId, answerDTO.getContents());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }

        if (!answerService.isSameWriter(id, session)) {
            return "redirect:/questions/" + questionId;
        }

        answerService.deleteAnswer(id);

        return "redirect:/questions/" + questionId;
    }
}
