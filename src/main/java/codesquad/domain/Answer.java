package codesquad.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    private String answer;

    @Builder
    public Answer(Question question, String answer, User writer) {
        this.question = question;
        this.answer = answer;
        this.writer = writer;
    }

    public boolean isSameWriter(Long id) {
        if (this.writer.isSameId(id)) {
            return true;
        }
        return false;
    }

    public boolean isSameWriter(User user) {
        if (this.writer.getId() == user.getId()) {
            return true;
        }
        return false;
    }
}
