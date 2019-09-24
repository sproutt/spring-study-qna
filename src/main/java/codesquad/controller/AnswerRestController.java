package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.dto.AnswerDTO;
import codesquad.dto.ResponseDTO;
import codesquad.exception.ResponseException;
import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import codesquad.util.ResponseUtil;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class AnswerRestController {

  private static final Logger logger = LoggerFactory.getLogger(AnswerRestController.class);

  private final AnswerService answerService;

  public AnswerRestController(AnswerService answerService) {
    this.answerService = answerService;
  }


  @PostMapping
  public ResponseDTO<Answer> register(@PathVariable Long questionId, @RequestBody AnswerDTO answerDTO,
      HttpSession session) throws ResponseException {

    HttpSessionUtils.checkLogining(session);

    return answerService
        .addAnswer(answerDTO.getContent(), HttpSessionUtils.getUserFromSession(session),
            questionId);

  }

  @DeleteMapping("/{id}")
  public ResponseDTO<Answer> delete(@PathVariable Long questionId, @PathVariable Long id,
      HttpSession session) throws ResponseException {

    HttpSessionUtils.checkLogining(session);

    return answerService
        .deleteAnswer(id, HttpSessionUtils.getUserFromSession(session));

  }
}
