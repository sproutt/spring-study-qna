package codesquad.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseAnswerDto {

    private long id;
    private String comment;
    private long questionId;
    private String writer;
}
