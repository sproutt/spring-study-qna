package codesquad.model.answer;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Data
public class Answer {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Getter
    @Setter
    @ManyToOne
    private User writer;

    @Getter
    @Setter
    @Column(nullable = false)
    private String content;

    public boolean isWriter(User user) {
        if (writer.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

}
