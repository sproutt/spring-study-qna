package codesquad.model;

import codesquad.dto.AnswerDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Column()
    @ColumnDefault(value = "false")
    private boolean deleted;

    @Lob
    private String contents;

    private LocalDateTime createDate = LocalDateTime.now();

    public Answer() {

    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public Answer(Question question, User writer, AnswerDTO answerDto) {
        this.question = question;
        this.writer = writer;
        this.contents = answerDto.getContents();
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) &&
                Objects.equals(question, answer.question) &&
                Objects.equals(writer, answer.writer) &&
                Objects.equals(contents, answer.contents) &&
                Objects.equals(createDate, answer.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, writer, contents, createDate);
    }
}
