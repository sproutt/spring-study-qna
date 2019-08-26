package codesquad.service;

import codesquad.dao.QuestionRepository;
import codesquad.domain.Question;
import codesquad.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  private QuestionRepository questionRepository;

  QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public void addQuestion(Question question) {
    questionRepository.save(question);
  }

  public Question getQuestionById(Long id) {
    return questionRepository.findById(id).get();
  }

  public List<Question> getQuestions() {
    List<Question> questions = new ArrayList<>();
    questionRepository.findAll().forEach(questions::add);

    return questions;
  }

  public void checkWriterIsUserOfSession(User loginUser, Long id) {
    if (!getQuestionById(id).isSameWriter(loginUser)) {
      throw new IllegalStateException("자신의 질문이 아닙니다");
    }
  }

  public void updateQuestion(User loginUser, Long id, Question updatedQuestion) {

    checkWriterIsUserOfSession(loginUser, id);

    Question question = getQuestionById(id);
    question.update(updatedQuestion);
    questionRepository.save(question);

  }

  public void deleteQuestion(User loginUser, Long id) {

    checkWriterIsUserOfSession(loginUser, id);

    questionRepository.deleteById(id);
  }

}
