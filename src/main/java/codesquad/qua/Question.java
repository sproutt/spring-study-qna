package codesquad.qua;

import codesquad.answer.Answer;
import codesquad.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    private String contents;

    private boolean deletedFlag;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    public Question(QuestionDto questionDto, User user) {
        this.writer = user;
        this.title = questionDto.getTitle();
        this.contents = questionDto.getContents();
        answers = new ArrayList<>();
        deletedFlag = false;
    }

    public void changeDeleteFlag() {
        deletedFlag = true;
    }

    public void update(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
    }

    public boolean equalsWriter(User user) {
        return writer.equals(user);
    }

    public boolean hasAnswers() {
        return answers.size() > 0;
    }
}