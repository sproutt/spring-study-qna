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

    @Column(nullable = false)
    private boolean deleted;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question() {
        this.deleted = false;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public boolean isSameWriter(User sessionedUser) {
        return this.writer.isSameUser(sessionedUser.getId());
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isAbleDelete() {
        return answers.stream().allMatch(answer -> answer.isSameWriter(this.writer));
    }
}
