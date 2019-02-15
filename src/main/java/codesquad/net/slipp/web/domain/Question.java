package codesquad.net.slipp.web.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @Getter @Setter
    private User writer;

    @OneToMany(mappedBy = "question")
    @Getter @Setter
    private List<Answer> answers;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    @Getter @Setter
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    @Getter @Setter
    private String contents;

    public void update(Question modifiedQuestion) {
        this.title = modifiedQuestion.title;
        this.contents = modifiedQuestion.contents;
    }

    public int getSize(){
        return answers.size();
    }
}
