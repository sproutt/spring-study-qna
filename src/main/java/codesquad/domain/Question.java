package codesquad.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String title;
    private String contents;

    @Builder
    public Question(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void changeQuestionInfo(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public boolean isSameUserId(User sessionedUser) {
        if (this.user.getUserId().equals(sessionedUser.getUserId())) {
            return true;
        }
        return false;
    }
}
