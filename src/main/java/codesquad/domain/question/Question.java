package codesquad.domain.question;

import codesquad.domain.user.User;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @Column(nullable = false, length = 20)
    private User writer;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

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

    public void update(Question updatedQuestion) {
        this.writer = updatedQuestion.getWriter();
        this.title = updatedQuestion.getTitle();
        this.contents = updatedQuestion.getContents();
    }
}
