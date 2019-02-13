package codesquad.net.slipp.web.domain;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String title;

    @Column(columnDefinition = "BLOB")
    private String contents;

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

    public String toString() {
        return "Question{" +
                "writer : " + writer +
                ", title : " + title +
                ", contents :" + contents +
                " }";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(Question modifiedQuestion) {
        this.title = modifiedQuestion.title;
        this.contents = modifiedQuestion.contents;

    }
}
