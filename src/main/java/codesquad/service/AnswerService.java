package codesquad.service;

import codesquad.dao.AnswerRepository;
import codesquad.domain.Answer;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private AnswerRepository answerRepository;

  public AnswerService(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  public void addAnswer(Answer answer, User loginUser) {
;
    answer.checkWriter(loginUser);

    answerRepository.save(answer);
  }

  public void deleteAnswer(Long id, User loginUser) {

    Answer answer = findAnswerById(id);
    answer.checkWriter(loginUser);

    answerRepository.delete(answer);
  }

  public void updateAnswer(Long id, User loginUser, Answer updatedAnswer) {

    Answer answer = findAnswerById(id);
    answer.checkWriter(loginUser);

    answer.update(updatedAnswer);
    answerRepository.save(answer);
  }


  public Iterable<Answer> getAllAnswers() {
    return answerRepository.findAll();
  }

  public Answer findAnswerById(Long id) {
    return answerRepository.findById(id)
        .orElseThrow(() -> (new IllegalStateException("데이터를 찾을 수 없습니다.")));
  }
}
