package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.*;
import codesquad.net.slipp.web.dto.AnswerDto;
import codesquad.net.slipp.web.service.AnswerService;
import codesquad.net.slipp.web.service.QuestionService;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/questions/{questionId}/answers")
    public Answer create(@PathVariable long questionId, @RequestBody AnswerDto answerDto, HttpSession session){

        return answerService.save(session, answerDto.getContents(), questionId);
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long questionId , @PathVariable long answerId, HttpSession session){

        answerService.delete(session,answerId);
        return "{a : 성공?}";
    }
}
