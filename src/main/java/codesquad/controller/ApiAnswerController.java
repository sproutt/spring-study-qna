package codesquad.controller;

import codesquad.domain.*;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
public class ApiAnswerController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public ApiAnswerController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/api/questions/{question-id}/answers")
    public Answer create(@PathVariable("question-id") Long questionId, @RequestBody AnswerDto answerDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId)
                                              .orElseThrow(NoSuchElementException::new);
        Answer answer = new Answer(loginUser, question, answerDto.getContents(), LocalDateTime.now());

        return answerRepository.save(answer);
    }

    @DeleteMapping("/api/questions/{question-id}/answers/{answer-id}")
    public Result<Answer> remove(@PathVariable("question-id") Long questionId, @PathVariable("answer-id") Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요한 서비스 입니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.findById(answerId)
                                        .orElseThrow(NoSuchElementException::new);

        if (!answer.isSameWriter(loginUser)) {
            return Result.fail("다른 사용자의 답변을 삭제할 수 없습니다.");
        }
        answer.setDeletedFlag(true);
        answerRepository.save(answer);

        return Result.ok(answer);
    }
}
