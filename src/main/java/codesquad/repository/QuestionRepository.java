package codesquad.repository;

import codesquad.entity.QuestionEntity;

import java.util.List;

public interface QuestionRepository {
    void post(QuestionEntity questionEntity);

    List<QuestionEntity> findAll();

    void setBaseTime(QuestionEntity questionEntity);
}
