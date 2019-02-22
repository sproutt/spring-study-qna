package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.exception.QuestionNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotMatchException;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;

    public Question findById(long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException(id)
        );
        return question;
    }

    public Question getAuthedQuestion(HttpSession session, long id) {
        if (!this.isSessionMatch(session, id)) {

            throw new SessionNotMatchException();
        }
        return this.findById(id);
    }

    public void update(Question modelQuestion, Question modifiedQuestion) {
        modelQuestion.update(modifiedQuestion);
        questionRepository.save(modelQuestion);
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Question save(Question question, User user) {
        question.setWriter(user);

        return questionRepository.save(question);
    }

    public boolean isSessionMatch(HttpSession session, long id) {
        if (!SessionUtil.isSessionMatch(session, findById(id).getWriter())) {

            return false;
        }
        return true;
    }

    public void delete(HttpSession session, long id) {
        if (!isSessionMatch(session, id)) {

            throw new SessionNotMatchException();
        }
        Question question = this.findById(id);
        question.setDeleted(true);
        question.getAnswers().stream().forEach(
                answer -> answer.setDeleted(true)
        );
        questionRepository.save(question);
    }
}
