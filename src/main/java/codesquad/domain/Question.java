package codesquad.domain;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

public class Question {

    private String writer;
    private String title;
    private String contents;
    private String createTime;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        createTime = now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
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

    public String getCreateTime() {
        return createTime;
    }
}
