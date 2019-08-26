package codesquad.service;

import codesquad.dao.AnswerRepository;
import codesquad.domain.Answer;
import codesquad.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private AnswerRepository answerRepository;

  public AnswerService(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  public void addAnswer(Answer answer) {
    answerRepository.save(answer);
  }

  public void deleteAnswer(Long id, User loginUser) {
    Answer answer = answerRepository.findById(id).get();

    checkWriter(answer, loginUser);

    answerRepository.deleteById(id);
  }

  public void updateAnswer(Long id, User loginUser, Answer updatedAnswer) {
    Answer answer = answerRepository.findById(id).get();

    checkWriter(answer, loginUser);

    answer.update(updatedAnswer);
    answerRepository.save(answer);
  }

  private void checkWriter(Answer answer, User user) {
    if (!answer.isWriter(user)) {
      throw new IllegalStateException("다른 사용자 입니다");
    }
  }

  public List<Answer> getAllAnswers() {
    List<Answer> answers = new ArrayList<>();
    answerRepository.findAll().forEach(answers::add);

    return answers;
  }

  public Answer getAnswerById(Long id) {
    return answerRepository.findById(id).get();
  }

}
