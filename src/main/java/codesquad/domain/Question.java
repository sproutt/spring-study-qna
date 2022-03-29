package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private final String writer;
    private final String title;
    private final String contents;
    private Long id;
    private LocalDateTime time;

    public Question(Long id, String writer, String title, String contents, LocalDateTime time) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public void createId(Long id) {
        this.id = id;
    }

    public void createWrittenTime(LocalDateTime writtenTime) {
        this.time = writtenTime;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
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

    public boolean equalsIndex(int index) {
        return this.id == index;
    }
}
