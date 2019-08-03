package codesquad.dto;

import codesquad.util.TimeGenerator;
import java.util.Date;

public class Question {

  private String writer;
  private String title;
  private String contents;
  private Date time;

  public Question(){
    time = new Date();
  }

  public String getTime() {
    return TimeGenerator.getFormatTime(time);
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }
}
