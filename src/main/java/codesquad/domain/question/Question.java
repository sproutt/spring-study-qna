package codesquad.domain.question;

import codesquad.domain.BaseTimeEntity;
import codesquad.domain.answer.Answer;
import codesquad.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Answer> answers;

    private Integer countOfAnswer = 0;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean equalsIndex(Long index) {
        return this.index.equals(index);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public void setCountOfAnswer(Integer countOfAnswer) {
        this.countOfAnswer = countOfAnswer;
    }

    public void update(Question updatedQuestion) {
        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }

    public boolean isSameWriter(User user) {
        return this.writer.isSameUser(user);
    }

    public void addCountOfAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }

    public void delete() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean canDelete() {
        if (countOfAnswer.equals(0)) {
            return true;
        }
        return false;
    }
}
