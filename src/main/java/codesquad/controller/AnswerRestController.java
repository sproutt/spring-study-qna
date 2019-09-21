package codesquad.controller;

import codesquad.VO.AnswerVO;
import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class AnswerRestController {

  private AnswerService answerService;

  public AnswerRestController(AnswerService answerService) {
    this.answerService = answerService;
  }


  @PostMapping("")
  public ResponseEntity<Answer> register(@PathVariable Long questionId, @RequestBody AnswerVO answerVo,
  HttpSession session){

    HttpSessionUtils.checkLogining(session);

    Answer answer = answerService.addAnswer(answerVo, HttpSessionUtils.getUserFromSession(session),questionId);

    return ResponseEntity.accepted().body(answer);
  }


}
