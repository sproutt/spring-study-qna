package codesquad.service;

import codesquad.dao.QuestionRepository;
import codesquad.domain.Question;
import codesquad.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

  public Question findQuestionById(Long id) {
    return questionRepository.findById(id)
        .orElseThrow(() -> (new IllegalStateException("데이터를 찾을 수 없습니다.")));
  }

  public Iterable<Question> getQuestions() {
    return questionRepository.findAll();
  }


  public void updateQuestion(User loginUser, Long id, Question updatedQuestion) {

    Question question = findQuestionById(id);
    question.checkWriter(loginUser);

    question.update(updatedQuestion);
    questionRepository.save(question);

  }

  public void deleteQuestion(User loginUser, Long id) {

    Question question = findQuestionById(id);
    question.checkWriter(loginUser);
    questionRepository.delete(question);
  }

  public void checkWriterIsUserOfSession(User loginUser, Long id){
    loginUser.checkId(id);
  }

}
