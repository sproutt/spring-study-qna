package codesquad.domain;

import codesquad.util.TimeGenerator;
import java.util.Date;
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
@Getter
@Setter
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
  private User writer;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
  private Question question;

  private String contents;
  private Date time;

  public Answer() {
    time = new Date();
  }

  public String getTime() {
    return TimeGenerator.formatTime(time);
  }

  public void update(Answer updatedAnswer) {
    contents = updatedAnswer.contents;
    time = updatedAnswer.time;
  }

  public boolean isWriter(User user){
    if(user== null){
      return false;
    }

    if(!writer.isSameUser(user)){
      return false;
    }

    return true;
  }
}
