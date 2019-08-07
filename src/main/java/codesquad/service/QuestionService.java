package codesquad.service;

import codesquad.dao.QuestionDao;
import codesquad.dao.UserDao;
import codesquad.dto.Question;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  @Resource(name = "questionDao")
  private QuestionDao questions;

  public void addQuestion(Question question) {
    questions.insertQuestion(question);
  }

  public Question getQuestion(String index) {
    return questions.getQuestion(Integer.parseInt(index));
  }

  public List<Question> getQuestions() {
    return questions.getQuestions();
  }
}
