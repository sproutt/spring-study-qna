package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.QuestionNotFoundException;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question findById(long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException(id)
        );
        return question;
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

    public void deleteById(long id) {

        questionRepository.deleteById(id);
    }

    public Question isSessionMatch(HttpSession session, long id) {
        SessionUtil.isSessionMatch(session, findById(id).getWriter());

        return findById(id);
    }
}
