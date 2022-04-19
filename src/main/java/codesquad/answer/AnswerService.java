package codesquad.answer;

import codesquad.qua.Question;
import codesquad.qua.QuestionRepository;
import codesquad.response.Result;
import codesquad.user.User;
import codesquad.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public ResponseAnswerDto create(long questionId, RequestAnswerDto requestAnswerDto, HttpSession session) {
        log.info("comment = {}", requestAnswerDto.getComment());

        Question question = questionRepository.findById(questionId)
                                              .orElseThrow(NoSuchElementException::new);

        User user = SessionUtil.getUserBySession(session);

        if (user == null) {
            return null;
        }

        Answer answer = new Answer(question, user, requestAnswerDto.getComment());
        answer.addQuestion(question);
        answerRepository.save(answer);

        return answer.toResponseAnswerDto();
    }

    @Transactional
    public Result<ResponseAnswerDto> remove(long questionId, long answerId, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);

        Answer answer = answerRepository.findQuestionFetchJoinById(answerId)
                                        .orElseThrow(NoSuchElementException::new);

        if (user == null) {
            return Result.fail("로그인 하세요");
        }

        if (!answer.equalsWriter(user)) {
            return Result.fail("다른 사람 답글은 삭제 못해요");
        }

        answer.changeDeletedFlag();

        return Result.ok(answer.toResponseAnswerDto());
    }
}