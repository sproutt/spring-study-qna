package codesquad.service;

import codesquad.dao.AnswerRepository;
import codesquad.dao.QuestionRepository;
import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.Result;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;

  public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
    this.answerRepository = answerRepository;
    this.questionRepository = questionRepository;
  }

  public Result<Answer> addAnswer(String content, User loginUser, Long questionId) {

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> (new IllegalStateException("데이터를 찾을 수 없습니다.")));
    Answer answer = question.addAnswer(content, loginUser);

    answerRepository.save(answer);

    return Result.ok(answer);
  }

  public Result<Answer> deleteAnswer(Long id, User loginUser) {

    Answer answer = findAnswerById(id);
    answer.checkWriter(loginUser);

    answerRepository.delete(answer);

    return Result.ok(answer);
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
