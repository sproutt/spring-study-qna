package codesquad.answer;

import codesquad.qua.Question;
import codesquad.qua.QuestionRepository;
import codesquad.user.User;
import codesquad.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@RestController
public class AnswerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/{question-id}/answers")
    public Answer create(@PathVariable("question-id") Long questionId, @RequestBody Answer answer, HttpSession session) {
        Question question = questionRepository.findById(questionId)
                                              .orElseThrow(NoSuchElementException::new);

        logger.info("답글 내용 : {}", answer.getComment());
        logger.info("답글 작성자 : {}", answer.getWriter());

        User user = SessionUtil.getUserBySession(session);

        if (user == null) {
            return null;
        }

        answer.setWriter(user.getName());
        answer.addQuestion(question);

        answerRepository.save(answer);

        return answer;
    }

    @DeleteMapping("/questions/{question-id}/answers/{answer-id}")
    @Transactional
    public Answer remove(@PathVariable("question-id") Long questionId,
                         @PathVariable("answer-id") Long answerId, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);

        Answer answer = answerRepository.findFetchJoinById(answerId)
                                        .orElseThrow(NoSuchElementException::new);

        if (user == null) {
            return null;
        }

        if (!answer.equalsWriter(user)) {
            return null;
        }

        answer.changeDeletedFlag();

        return answer;
    }
}
