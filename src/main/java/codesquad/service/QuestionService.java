package codesquad.service;

import codesquad.exception.QuestionNotExistException;
import codesquad.exception.UserNotEqualException;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotExistException::new);
    }

    public void update(Question question, User writer) {
        question.update(question, writer);
        questionRepository.save(question);
    }

    public void delete(User sessionedUser, Long id) {
        Question questionToDelete = getQuestionById(id);
        if (!questionToDelete.isEqualWriter(sessionedUser)) {
            throw new UserNotEqualException();
        }
        questionRepository.delete(questionToDelete);
    }

    public Question getUpdatedQuestion(User sessionedUser, Long id) {
        Question questionToUpdate = getQuestionById(id);
        if (!questionToUpdate.isEqualWriter(sessionedUser)) {
            throw new UserNotEqualException();
        }
        return questionToUpdate;
    }
}
