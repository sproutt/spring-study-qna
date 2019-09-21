package codesquad.controller;

import codesquad.domain.Answer;
import org.springframework.http.ResponseEntity;

public class Result {

  public static ResponseEntity<Answer> ok(Answer answer){
    return ResponseEntity.accepted().body(answer);
  }

  public static ResponseEntity<Answer> fail(){
    return ResponseEntity.badRequest().body(null);
  }
}
