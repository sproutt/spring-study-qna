package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String userId;
    private String title;
    private String contents;
    private String time;

    public void setCurrentTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.time = currentDateTime.format(dateTimeFormatter);
    }

    public void setUserInfo(User loginUser) {
        this.writer = loginUser;
    }

    public void changeInfo(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
        setCurrentTime();
    }

}
