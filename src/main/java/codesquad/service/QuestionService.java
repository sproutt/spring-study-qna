package codesquad.service;

import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.QuestionRepository;
import codesquad.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public void update(Question question, User writer) {
        question.update(question, writer);
        questionRepository.save(question);
    }

    public void delete(User sessionedUser, Long id) {
        Question questionToDelete = getQuestionById(id);
        if (!questionToDelete.isEqualWriter(sessionedUser)) {
            throw new CustomException("세션유저의 글이 아닙니다");
        }
        questionRepository.delete(questionToDelete);
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Question getUpdatedQuestion(User sessionedUser, Long id) {
        Question questionToUpdate = getQuestionById(id);
        if (!questionToUpdate.isEqualWriter(sessionedUser)) {
            throw new CustomException("세션유저의 글이 아닙니다");
        }
        return questionToUpdate;
    }
}
