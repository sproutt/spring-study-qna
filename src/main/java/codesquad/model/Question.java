package codesquad.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String time;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public Long findWriterId() {
        return writer.getId();
    }

    public boolean isSameWriter(User sessionedUser) {
        return this.writer.equals(sessionedUser);
    }
}
