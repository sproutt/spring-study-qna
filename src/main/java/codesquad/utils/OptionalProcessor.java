package codesquad.utils;

import codesquad.model.Question;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalProcessor {
    @Autowired
    QuestionRepository questionRepository;

    public Question optionalToQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}