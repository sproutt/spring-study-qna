package codesquad.controller;

import codesquad.model.answer.AnswerDto;
import codesquad.model.content.Content;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/question/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public AnswerDto create(@PathVariable Long questionId, @RequestBody Content content, HttpSession session) {
        return new AnswerDto(questionService.saveAnswer(session, content, questionId));
    }

    @DeleteMapping("/{id}")
    public AnswerDto delete(@PathVariable Long id, HttpSession session) {
        return new AnswerDto(questionService.deleteAnswer(id, session));
    }

}