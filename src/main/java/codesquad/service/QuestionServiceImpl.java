package codesquad.service;

import codesquad.entity.QuestionEntity;
import codesquad.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void postQuestion(QuestionEntity questionEntity) {
        questionRepository.post(questionEntity);
        questionRepository.setBaseTime(questionEntity);
    }

    @Override
    public List<QuestionEntity> findQuestions() {
        return questionRepository.findAll();
    }
}
