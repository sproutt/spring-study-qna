package codesquad.answer;

import codesquad.qua.Question;
import codesquad.user.User;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    private String writer;

    private String comment;

    private boolean deletedFlag = false;

    public boolean isDeletedFlag() {
        return deletedFlag;
    }

    public void changeDeletedFlag() {
        deletedFlag = true;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addQuestion(Question question) {
        question.getAnswers().add(this);
        this.question = question;
    }

    public boolean equalsWriter(User user) {
        return writer.equals(user.getName());
    }
}
