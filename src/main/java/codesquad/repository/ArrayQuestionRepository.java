package codesquad.repository;

import codesquad.entity.QuestionEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArrayQuestionRepository implements QuestionRepository {
    private final List<QuestionEntity> questions = new ArrayList<>();

    @Override
    public void post(QuestionEntity questionEntity) {
        questions.add(questionEntity);
    }

    @Override
    public List<QuestionEntity> findAll() {
        return questions;
    }

    @Override
    public void setBaseTime(QuestionEntity questionEntity) {
        questionEntity.setCreatedDate(LocalDateTime.now());
    }
}
