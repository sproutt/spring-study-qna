package codesquad.net.slipp.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    private String contents;

    @Column(nullable = false)
    private Boolean deleted = false;

    public void update(Question modifiedQuestion) {
        this.title = modifiedQuestion.title;
        this.contents = modifiedQuestion.contents;
    }

    public int getSize(){
        return answers.size();
    }
}
