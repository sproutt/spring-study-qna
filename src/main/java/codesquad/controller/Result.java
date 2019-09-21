package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;

public class Result {

  public static ResponseEntity<Answer> ok(Answer answer){
    return ResponseEntity.accepted().body(answer);
  }

  public static ResponseEntity<ErrorDTO> fail(String error){
    return ResponseEntity.badRequest().body(new ErrorDTO(error));
  }
}
