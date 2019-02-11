package codesquad.service;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Question findById(Long id) {
        Optional<Question> byId = questionRepository.findById(id);
        return byId.orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public void deleteById(Long id) {
        questionRepository.delete(findById(id));
    }

    public void updateById(Long id, Question modifiedQuestion) {
        Question question = findById(id);
        question.update(modifiedQuestion);
        save(question);
    }
}
