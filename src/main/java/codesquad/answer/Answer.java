package codesquad.answer;

import codesquad.qua.Question;

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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public String getComment() {
        return comment;
    }

    public void addQuestion(Question question) {
        question.getAnswers().add(this);
        this.question = question;
    }
}
