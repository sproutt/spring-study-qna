package codesquad.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Question {
    private String writer;
    private String title;
    private String contents;
    private String time;
}
