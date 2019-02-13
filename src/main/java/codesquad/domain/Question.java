package codesquad.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_to_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private String createdDate;

    private boolean deleted = false;

    public void update(Question modifiedQuestion) {
        this.title = modifiedQuestion.title;
        this.contents = modifiedQuestion.contents;
    }

    public void addAnswer(Answer answer) {
        this.getAnswers().add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        this.getAnswers().remove(answer);
        answer.setQuestion(null);
    }
}
