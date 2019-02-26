package codesquad.service;

import codesquad.exception.QuestionNotFoundException;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import codesquad.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void saveQuestion(Question question, HttpSession session) {
        question.setWriter(HttpSessionUtils.getSessionedUser(session));
        question.setTime(TimeUtils.getCurrentTime());
        questionRepository.save(question);
    }

    public Iterable<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

    public void updateQuestion(Question question, Question updatedQuestion) {
        question.update(updatedQuestion);
        question.setTime(TimeUtils.getCurrentTime());
        questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.delete(questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new));
    }

    public Long findWriterIdByQuestionId(Long questionId) {
        return findById(questionId).findWriterId();
    }

    public boolean isSameWriter(Long id, User sessionedUser) {
        return questionRepository.findById(id).orElseThrow(RuntimeException::new).isSameWriter(sessionedUser);
    }
}
