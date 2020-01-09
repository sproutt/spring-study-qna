package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;
    private String answer;
    private String time;

    public void setCurrentTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.time = currentDateTime.format(dateTimeFormatter);
    }

    public void setQuestionInfo(Question question) {
        this.question = question;
    }

    public void setUserInfo(User loginUser) {
        this.writer = loginUser;
    }
    public boolean isSameUser(User loginUser){
        return this.writer.getId().equals(loginUser.getId());
    }
}
