package codesquad.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Question {

  private String writer;
  private String title;
  private String contents;
  private String time;

  public String getTime() {
    return time;
  }

  public Question(){
    time = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
        .format(new Date());
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
