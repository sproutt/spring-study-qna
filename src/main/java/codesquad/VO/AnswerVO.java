package codesquad.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerVO {

  private String content;

  public void AnswerVO(String content){
    this.content = content;
  }

}
