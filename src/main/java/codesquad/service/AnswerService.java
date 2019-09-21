package codesquad.service;

import codesquad.dto.AnswerDTO;
import codesquad.dao.AnswerRepository;
import codesquad.dao.QuestionRepository;
import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private AnswerRepository answerRepository;
  private QuestionRepository questionRepository;

  public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
    this.answerRepository = answerRepository;
    this.questionRepository = questionRepository;
  }

  public Answer addAnswer(AnswerDTO answerDTO, User loginUser, Long questionId) {

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> (new IllegalStateException("데이터를 찾을 수 없습니다.")));
    Answer answer = new Answer(answerDTO.getContent(), loginUser, question);

    answerRepository.save(answer);
    return answer;
  }

  public Answer deleteAnswer(Long id, User loginUser) {

    Answer answer = findAnswerById(id);
    answer.checkWriter(loginUser);

    answerRepository.delete(answer);
    return answer;
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
