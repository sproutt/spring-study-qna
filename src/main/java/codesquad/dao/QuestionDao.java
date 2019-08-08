package codesquad.dao;

import codesquad.dto.Question;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao {

  private List<Question> questionDB = new ArrayList<>();

  public void insertQuestion(Question question) {
    questionDB.add(question);
  }

  public Question getQuestion(int index){
    return questionDB.get(index);
  }

  public List<Question> getQuestions() {
    return questionDB;
  }
}
