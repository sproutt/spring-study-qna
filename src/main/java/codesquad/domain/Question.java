package codesquad.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    private String title;
    private String contents;

    @Builder
    public Question(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
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

    public void updateAnswers(List<Answer> answers){
        this.answers = answers;
    }
}
