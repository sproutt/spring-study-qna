package codesquad.web;

import codesquad.domain.Result;
import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtil;
import codesquad.web.dto.AnswerSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/questions/{index}/answers")
public class ApiAnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long index, @RequestBody AnswerSaveRequestDto answerSaveRequestDto, HttpSession httpSession) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return null;
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
        Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        Answer answer = new Answer(sessionedUser, savedQuestion, answerSaveRequestDto.getContents());
        savedQuestion.addCountOfAnswer();

        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result<Answer> delete(@PathVariable Long index, @PathVariable Long id, HttpSession httpSession) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return Result.fail("세션이 만료되었습니다. 다시 로그인 해주세요.");
        }

        Answer savedAnswer = answerRepository.findQuestionFetchJoinById(id).orElseThrow(() -> new NoSuchElementException("답변이 존재하지 않습니다."));
        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

        if (!savedAnswer.isSameWriter(sessionedUser)) {
            return Result.fail("다른 사람의 게시글을 삭제할 수 없습니다.");
        }

        savedAnswer.delete();

        answerRepository.save(savedAnswer);

        Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        savedQuestion.deleteAnswer();

        questionRepository.save(savedQuestion);

        return Result.ok(savedAnswer);
    }
}
