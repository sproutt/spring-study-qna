package codesquad.Model;

import codesquad.utils.Date;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Question {
    private String writer;
    private String title;
    private String contents;
    private String time;

    public Question() {
        this.time = Date.getCurrentTime();
    }
}
