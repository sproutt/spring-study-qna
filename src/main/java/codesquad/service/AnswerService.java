package codesquad.service;

import codesquad.domain.Answer;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AnswerService {
    void create(Long questionId, String answer, HttpSession session);

    List<Answer> findAnswers(Long questionId);

    void delete(Long id, HttpSession session);
}
