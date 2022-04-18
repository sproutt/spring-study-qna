package codesquad.domain.answer;

import codesquad.domain.question.Question;
import codesquad.domain.user.User;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(columnDefinition = "TEXT", length = 300, nullable = false)
    private String contents;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public boolean isSameWriter(User user) {
        return this.writer.isSameUser(user);
    }

    public void delete() {
        isDeleted = true;
    }
}
