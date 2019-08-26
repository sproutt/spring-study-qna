package codesquad.service;

import codesquad.dao.AnswerRepository;
import codesquad.domain.Answer;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private AnswerRepository answerRepository;

  public AnswerService(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  public void addAnswer(Answer answer){
    answerRepository.save(answer);
  }
}
