package codesquad.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Answer> answers;

    private String title;
    private String contents;
    private int answersNum;

    @Builder
    public Question(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.answersNum = 0;
    }

    public void changeQuestionInfo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameUserId(User sessionedUser) {
        if (this.user.getUserId().equals(sessionedUser.getUserId())) {
            return true;
        }
        return false;
    }

    public boolean isCanDelete() {
        if (this.answers.isEmpty()) {
            return true;
        }
        for (Answer answer : answers) {
            if (!answer.isSameWriter(user.getId())) {
                return false;
            }
        }
        return true;
    }

    public void updateAnswersNum() {
        this.answersNum = answers.size();
    }
}
