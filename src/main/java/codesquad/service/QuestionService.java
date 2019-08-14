package codesquad.service;

import codesquad.dao.QuestionRepository;
import codesquad.dto.Question;
import java.util.ArrayList;
import java.util.List;
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

  public Question getQuestion(Long id) {
    return questionRepository.findById(id).get();
  }

  public List<Question> getQuestions() {
    List<Question> questions = new ArrayList<>();
    questionRepository.findAll().forEach(questions::add);

    return questions;
  }

  public void updateQuestion(Long id, Question newQuestion){
    Question question = questionRepository.findById(id).get();
    question.update(newQuestion);
    questionRepository.save(question);
  }

  public void deleteQuestion(Long id){
    questionRepository.deleteById(id);
  }
}
