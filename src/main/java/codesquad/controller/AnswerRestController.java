package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.service.AnswerService;
import codesquad.util.HttpSessionUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class AnswerRestController {

  private AnswerService answerService;

  public AnswerRestController(AnswerService answerService) {
    this.answerService = answerService;
  }


  @PostMapping
  public Map<String, Object> register(@PathVariable Long questionId, Answer answer,
  HttpSession session){

    HttpSessionUtils.checkLogining(session);

    answerService.addAnswer(answer, HttpSessionUtils.getUserFromSession(session));

    Map<String, Object> map= new HashMap<>();
    map.put("answer", answer);

    return map;
  }


}
