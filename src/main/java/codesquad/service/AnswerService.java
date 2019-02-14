package codesquad.service;

import codesquad.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

}
