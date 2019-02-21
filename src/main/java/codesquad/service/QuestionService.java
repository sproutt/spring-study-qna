package codesquad.service;

import codesquad.exception.CannotDeleteQuestionException;
import codesquad.exception.QuestionNotExistException;
import codesquad.exception.UserNotEqualException;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

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
        Question question = getQuestionById(id);
        boolean isDeleted = false;
        if (question.getAnswers().size() == 0) {
            isDeleted = true;
        }
        if (question.isEqualWriter(sessionedUser)) {
            isDeleted = true;
        }
        if (question.isAllSameUser()) {
            isDeleted = true;
        }
        if (isDeleted) {
            question.setDeletedAnswer();
            questionRepository.save(question);
        } else {
            throw new CannotDeleteQuestionException();
        }
    }

    public Question getUpdatedQuestion(User sessionedUser, Long id) {
        Question questionToUpdate = getQuestionById(id);
        if (!questionToUpdate.isEqualWriter(sessionedUser)) {
            throw new UserNotEqualException();
        }
        return questionToUpdate;
    }
}
