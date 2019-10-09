package codesquad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /*
    @Column(nullable = false, length = 20)
    private String writer;
    */

    private String userId;
    private String title;
    private String contents;
    private String time;

    public void checkCurrentTime() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        this.time = date.format(today) + " " + time.format(today);
    }


    public void checkUserInfo(User loginUser) {
        this.writer = loginUser;
    }

    public void changeQuestionInfo(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
        checkCurrentTime();
    }

    public boolean isSameUserId(User loginUser) {
        if (writer.getUserId() == loginUser.getUserId()) {
            return true;
        } else {
            return false;
        }

    }


}
