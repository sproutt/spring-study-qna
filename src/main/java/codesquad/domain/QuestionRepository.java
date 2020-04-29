package codesquad.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepository {
    private List<Question> questions;

    public QuestionRepository() {
        questions = new ArrayList<>();
    }

    public void insert(Question question) {
        this.questions.add(question);
    }

    public List<Question> findAll() {
        return questions;
    }

    public Question find(int index) {
        return questions.get(index - 1);
    }
}
