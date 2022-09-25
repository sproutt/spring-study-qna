package codesquad.service;

import codesquad.entity.QuestionEntity;

import java.util.List;

public interface QuestionService {
    void postQuestion(QuestionEntity questionEntity);

    List<QuestionEntity> findQuestions();
}
