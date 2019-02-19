package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.*;
import codesquad.net.slipp.web.dto.AnswerDto;
import codesquad.net.slipp.web.result.Result;
import codesquad.net.slipp.web.service.AnswerService;
import codesquad.net.slipp.web.service.QuestionService;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/questions/{questionId}/answers")
    public JSONObject create(@PathVariable long questionId, @RequestBody AnswerDto answerDto, HttpSession session){
        Answer answer;
        try{
             answer = answerService.save(session, answerDto.getContents(), questionId);
        }catch (Exception e){

            return Result.fail(e.getMessage());
        }

        return Result.ok(answer);
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public JSONObject deleteAnswer(@PathVariable long questionId , @PathVariable long answerId, HttpSession session){
        try{
            answerService.delete(session,answerId);
        }catch (Exception e){

            return Result.fail(e.getMessage());
        }

        return Result.ok(answerId);
    }
}
