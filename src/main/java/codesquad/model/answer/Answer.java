package codesquad.model.answer;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @ManyToOne
    @JsonBackReference
    private Question question;

    @ManyToOne
    private User writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private boolean deleted = false;

    public Answer(){

    }

    public Answer(User writer,Question question,String content){
        this.writer = writer;
        this.question = question;
        this.content = content;
        this.date = new Date().toString();
    }

    public boolean isWriter(User user) {
        if (writer.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

}
