package codesquad.answer;

import codesquad.qua.Question;
import codesquad.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    private String comment;

    private boolean deletedFlag;

    public Answer(Question question, User writer, String comment) {
        this.question = question;
        this.writer = writer;
        this.comment = comment;
        deletedFlag = false;
    }

    public void changeDeletedFlag() {
        deletedFlag = true;
    }

    public void addQuestion(Question question) {
        question.getAnswers().add(this);
        this.question = question;
    }

    public boolean equalsWriter(User user) {
        return writer.equals(user);
    }

    public ResponseAnswerDto toResponseAnswerDto() {
        return new ResponseAnswerDto(id, comment, question.getId(), writer.getName());
    }
}
