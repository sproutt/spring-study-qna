package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteQuestionDto {
    private String title;
    private String contents;
    private LocalDateTime time;

    public WriteQuestionDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
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

    public void createWrittenTime(LocalDateTime writtenTime) {
        this.time = writtenTime;
    }


    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return time.format(formatter);
    }
}
