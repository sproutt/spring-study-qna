package codesquad.answer;

import codesquad.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiAnswerController {

    private final AnswerService answerService;

    @PostMapping("/questions/{question-id}/answers")
    public ResponseAnswerDto create(@PathVariable("question-id") Long questionId, @RequestBody RequestAnswerDto requestAnswerDto, HttpSession session) {
        return answerService.create(questionId, requestAnswerDto, session);
    }

    @DeleteMapping("/questions/{question-id}/answers/{answer-id}")
    public Result<ResponseAnswerDto> remove(@PathVariable("question-id") Long questionId,
                                            @PathVariable("answer-id") Long answerId, HttpSession session) {
        return answerService.remove(questionId, answerId, session);
    }
}
