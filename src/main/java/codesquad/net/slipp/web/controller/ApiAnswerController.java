package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.Answer;
import codesquad.net.slipp.web.dto.AnswerDto;
import codesquad.net.slipp.web.utils.JsonResponse;
import codesquad.net.slipp.web.service.AnswerService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public JSONObject create(@PathVariable long questionId, @RequestBody AnswerDto answerDto, HttpSession session) {
        Answer answer;
        try {
            answer = answerService.save(session, answerDto.getContents(), questionId);
        } catch (Exception e) {

            return JsonResponse.fail(e.getMessage());
        }

        return JsonResponse.ok(answer);
    }

    @DeleteMapping("/{answerId}")
    public JSONObject deleteAnswer(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        Answer answer;
        try {
            answer = answerService.delete(session, answerId);
        } catch (Exception e) {

            return JsonResponse.fail(e.getMessage());
        }

        return JsonResponse.ok(answer);
    }
}
