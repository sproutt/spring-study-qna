package codesquad.service;

import codesquad.dao.QuestionDao;
import codesquad.dto.Question;
import codesquad.util.TypeGenerator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  @Autowired
  private QuestionDao questions;

  public void addQuestion(Question question){
    questions.insertQuestion(question);
  }

  public Question getQuestion(String index){
    return questions.getQuestion(TypeGenerator.toInt(index));
  }

  public List<Question> getQuestions(){
    return questions.getQuestions();
  }
}
