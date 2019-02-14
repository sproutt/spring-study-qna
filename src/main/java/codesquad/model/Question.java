package codesquad.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 40)
    private String contents;

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

    public void update(Question updateQuestion, User writer) {
        this.setWriter(writer);
        this.setTitle(updateQuestion.title);
        this.setContents(updateQuestion.contents);
    }

    public boolean isEqualWriter(User user) {
        if (this.getWriter().getName().equals(user.getName())) {
            return true;
        } else
            return false;
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }
}
