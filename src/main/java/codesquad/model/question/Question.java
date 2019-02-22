package codesquad.model.question;

import codesquad.model.answer.Answer;
import codesquad.model.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Question {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(nullable = false)
    private String contents;

    @Getter
    @Setter
    @OneToMany(mappedBy="question")
    private List<Answer> answers;

    public void update(Question question) {
        this.contents = question.contents;
        this.title = question.title;
    }

    public boolean isWriter(User user){
        if(this.writer.getId().equals(user.getId())){
            return true;
        }
        return false;
    }

}
