package codesquad.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    private String title;
    private String contents;
    private LocalDateTime time;

    public Question(User writer, String title, String contents, LocalDateTime time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public Question() {

    }

    public User getWriter() {
        return writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void createWrittenTime(LocalDateTime writtenTime) {
        this.time = writtenTime;
    }

    public Long getId() {
        return id;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return time.format(formatter);
    }

    public void write(User writer, WriteQuestionDto writeQuestionDto, LocalDateTime writtenTime) {
        this.writer = writer;
        this.title = writeQuestionDto.getTitle();
        this.contents = writeQuestionDto.getContents();
        this.time = writtenTime;
    }

}
