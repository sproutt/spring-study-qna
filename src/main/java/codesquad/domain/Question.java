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


    @Column(nullable = false, length = 20)
    private String writer;

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
        this.writer = loginUser.getName();
        this.userId  = loginUser.getUserId();
    }

    public void changeQuestionInfo(Question editedQuestion){
        this.title = editedQuestion.getTitle();
        this.contents = editedQuestion.getContents();
        checkCurrentTime();
    }

}
