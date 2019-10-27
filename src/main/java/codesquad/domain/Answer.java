package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

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

    private String writer;
    private String reply;
    private String time;
    private Long writerId;

    public void setCurrentTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.time = currentDateTime.format(dateTimeFormatter);
    }

    public void setQuestionInfo(Question question) {
        this.question = question;
    }

    public void setUserInfo(User loginUser) {
        this.writer = loginUser.getName();
        this.writerId = loginUser.getId();
    }
    public boolean isSameUser(User loginUser){
        return this.writerId.equals(loginUser.getId());
    }
}
