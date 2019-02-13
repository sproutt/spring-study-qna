package codesquad.net.slipp.web.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @Getter
    private User writer;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    @Getter @Setter
    private String title;

    @Column(columnDefinition = "BLOB")
    @Getter @Setter
    private String contents;

    public void update(Question modifiedQuestion) {
        this.title = modifiedQuestion.title;
        this.contents = modifiedQuestion.contents;

    }
}
