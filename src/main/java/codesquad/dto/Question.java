package codesquad.dto;

import codesquad.util.TimeGenerator;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;


public class Question {

  @Setter @Getter
  private String writer;
  @Setter @Getter
  private String title;
  @Setter @Getter
  private String contents;
  private Date time;

  public Question(){
    this.time = new Date();
  }

  public String getTime(){
    return TimeGenerator.formatTime(time);
  }
}
