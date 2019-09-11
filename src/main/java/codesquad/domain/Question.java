package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Setter
@Getter
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

  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Answer> answers;

  @CreationTimestamp
  private LocalDateTime createdDate;
  @UpdateTimestamp
  private LocalDateTime updatedDate;

  public String getTime() {
    if(updatedDate == null){
      return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    return updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

  public void update(Question updatedQuestion) {
    this.writer = updatedQuestion.writer;
    this.title = updatedQuestion.title;
    this.contents = updatedQuestion.contents;
  }

  public void checkWriter(User user) {
    if(writer.getId() != user.getId()){
      throw new IllegalStateException("자신의 질문이 아닙니다");
    }
  }
}
