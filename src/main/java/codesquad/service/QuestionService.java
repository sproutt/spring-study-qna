package codesquad.service;

import codesquad.domain.Question;

public interface QuestionService {
    Iterable<Question> findQuestions();

    void create(String title, String contents);

    Question findQuestion(Long id);

    boolean checkUserId(Long id);

    void updateQuestionInfo(Long id,String title,String contents);

    void deleteQuestion(Long id);
}
