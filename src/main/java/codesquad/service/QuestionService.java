package codesquad.service;

import codesquad.dao.QuestionRepository;
import codesquad.dto.Question;
import codesquad.util.HttpSessionUtils;
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

  public String updateQuestion(Long id, Question updatedQuestion) {

    Question question = getQuestionById(id);

    question.update(updatedQuestion);
    questionRepository.save(question);

    return "redirect:/";
  }

  public String deleteQuestionService(Long id, HttpSession session) {

    Question question = getQuestionById(id);
    HttpSessionUtils.checkWriterOfQuestionFromSession(question, session);

    questionRepository.deleteById(id);

    return "redirect:/";
  }

}
