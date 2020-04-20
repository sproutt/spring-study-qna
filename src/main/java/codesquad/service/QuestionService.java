package codesquad.service;

import codesquad.domain.Question;

import javax.servlet.http.HttpSession;

public interface QuestionService {
    Iterable<Question> findQuestions();

    void create(String title, String contents, HttpSession session);

    Question findQuestion(Long id);

    boolean checkUserId(Long id, HttpSession session);

    void updateQuestionInfo(Long id, String title, String contents);

    void deleteQuestion(Long id);
}
