package codesquad.dto;

import codesquad.util.TimeGenerator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter @Getter
  @Column(nullable = false)
  private String writer;

  @Setter @Getter
  @Column(nullable = false)
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
