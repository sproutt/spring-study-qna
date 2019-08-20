package codesquad.service;

import codesquad.dao.QuestionRepository;
import codesquad.dto.Question;
import codesquad.util.HttpSessionUtils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

  public String updateQuestion(Long id, Question newQuestion, HttpSession session) {
    if (HttpSessionUtils.isLoginUser(session)) {
      return HttpSessionUtils.LOGIN_URL;
    }

    Question question = getQuestionById(id);
    if (!HttpSessionUtils.isSameWriterFromSession(question, session)) {
      throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
    }

    question.update(newQuestion);
    questionRepository.save(question);

    return "redirect:/";
  }

  public String deleteQuestionService(Long id, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return HttpSessionUtils.LOGIN_URL;
    }

    Question question = getQuestionById(id);
    HttpSessionUtils.checkWriterOfQuestionFromSession(id, question, session);

    questionRepository.deleteById(id);

    return "redirect:/";
  }

  public String questionInfoService(Long id, Model model, HttpSession session){
    if (!HttpSessionUtils.isLoginUser(session)) {
      return HttpSessionUtils.LOGIN_URL;
    }

    Question question = getQuestionById(id);
    HttpSessionUtils.checkWriterOfQuestionFromSession(id, question, session);

    model.addAttribute("question", question);

    return "qna/updateForm";
  }
}
