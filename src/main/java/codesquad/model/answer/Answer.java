package codesquad.model.answer;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
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
    private boolean deleted = false;

    public Answer(){
        this.id = null;
        this.writer = null;
        this.question = null;
        this.content = null;
    }

    public Answer(User writer,Question question,String content){
        this.id = null;
        this.writer = writer;
        this.question = question;
        this.content = content;
    }

    public boolean isWriter(User user) {
        if (writer.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

}
