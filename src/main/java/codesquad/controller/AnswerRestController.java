package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.dto.AnswerDTO;
import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

  private AnswerService answerService;

  public AnswerRestController(AnswerService answerService) {
    this.answerService = answerService;
  }


  @PostMapping("")
  public ResponseEntity register(@PathVariable Long questionId,
      @RequestBody AnswerDTO answerDTO,
      HttpSession session) {

    HttpSessionUtils.checkLogining(session);

    try {
      Answer answer = answerService
          .addAnswer(answerDTO.getContent(), HttpSessionUtils.getUserFromSession(session), questionId);

      return Result.ok(answer);

    } catch (Exception e) {
      e.printStackTrace();

      return Result.fail(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long questionId, @PathVariable Long id,
      HttpSession session) {

    HttpSessionUtils.checkLogining(session);

    try {
      Answer answer = answerService.deleteAnswer(id, HttpSessionUtils.getUserFromSession(session));

      return Result.ok(answer);

    } catch (Exception e) {
      e.printStackTrace();

      return Result.fail(e.getMessage());
    }
  }
}
