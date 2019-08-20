package codesquad.dto;

import codesquad.util.TimeGenerator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
  private User writer;

  @Column(nullable = false, length = 50)
  private String title;

  private String contents;
  private Date time;

  public Question(){
    this.time = new Date();
  }

  public String getTime(){
    return TimeGenerator.formatTime(time);
  }

  public void update(Question newQuestion){
    this.writer = newQuestion.writer;
    this.title = newQuestion.title;
    this.contents = newQuestion.contents;
    this.time = newQuestion.time;
  }

  public boolean isSameWriter(User user){
    return writer.getId() == user.getId();
  }
}
