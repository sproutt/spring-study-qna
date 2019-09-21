package codesquad.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

  private String content;

  public void AnswerVO(String content){
    this.content = content;
  }

}
