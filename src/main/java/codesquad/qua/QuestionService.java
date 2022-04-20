package codesquad.qua;

import codesquad.answer.Answer;
import codesquad.exception.QuestionDeleteException;
import codesquad.exception.QuestionEditException;
import codesquad.user.User;
import codesquad.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public boolean setCreateForm(HttpSession session) {
        User user = SessionUtil.getUserBySession(session);

        return user != null;
    }

    public boolean create(QuestionDto questionDto, HttpSession session) {
        log.info("작성자 = {}", questionDto.getWriter());
        log.info("제목 = {}", questionDto.getTitle());

        User user = SessionUtil.getUserBySession(session);

        if (user == null) {
            return false;
        }

        questionRepository.save(new Question(questionDto, user));

        return true;
    }

    public List<Question> list() {
        return questionRepository.findAll();
    }

    public void setUpdateForm(long id, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);
        Question savedQuestion = findQuestionById(id);

        if (user == null || !isQuestionMatchUser(user, savedQuestion)) {
            throw new QuestionEditException();
        }

        log.info("user: {}", user.getName());
        log.info("question: {}", savedQuestion.getWriter());
    }

    public void update(Question changedQuestion, long id, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);
        Question savedQuestion = findQuestionById(id);

        if (user == null || !isQuestionMatchUser(user, savedQuestion)) {
            throw new QuestionEditException();
        }

        log.info("user: {}", user.getName());
        log.info("question: {}", savedQuestion.getWriter());

        savedQuestion.update(changedQuestion);
    }

    public void remove(long id, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);
        Question savedQuestion = findQuestionById(id);

        log.info("user: {}", user.getName());
        log.info("question: {}", savedQuestion.getWriter());

        if (user == null || !isQuestionMatchUser(user, savedQuestion)) {
            throw new QuestionEditException();
        }

        if (!canDeleteQuestion(savedQuestion, user)) {
            throw new QuestionDeleteException();
        }

        savedQuestion.deleteQuestion();
    }

    public long countAnswers(long id) {
        Long countAnswer = questionRepository.countNotDeletedAnswers(id);

        if(countAnswer == null) {
            return 0L;
        }

        return countAnswer;
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                                 .orElseThrow(NoSuchElementException::new);
    }

    private boolean canDeleteQuestion(Question deletedQuestion, User user) {
        if (deletedQuestion.hasAnswers()) {
            for (Answer answer : deletedQuestion.getAnswers()) {
                if (!answer.equalsWriter(user)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isQuestionMatchUser(User loginUser, Question question) {
        return question.equalsWriter(loginUser);
    }
}
