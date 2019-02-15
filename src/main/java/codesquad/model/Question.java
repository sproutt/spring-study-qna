package codesquad.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class Question {
    private String writer;
    private String title;
    private String contents;
    private String time;

    public Question() {
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }
}
