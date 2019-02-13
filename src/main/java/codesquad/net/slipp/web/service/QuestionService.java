package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.QuestionNotFoundException;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public Question findById(long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException(id)
        );
        return question;
    }


    public void update(Question modelQuestion, Question updatedQuestion) {
        modelQuestion.setTitle(updatedQuestion.getTitle());
        modelQuestion.setContents(updatedQuestion.getContents());
        questionRepository.save(modelQuestion);
    }

    public Iterable<Question> findAll() {

        return questionRepository.findAll();
    }

    public Question save(Question question) {

        return questionRepository.save(question);
    }
}
