package codesquad.model.answer;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
public class Answer {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Getter
    @Setter
    @ManyToOne
    User writer;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    String content;

}
