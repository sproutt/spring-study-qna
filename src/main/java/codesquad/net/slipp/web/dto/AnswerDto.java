package codesquad.net.slipp.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {

    private String contents;

    public AnswerDto(){

    }

    public AnswerDto(String contents){
        this.contents=contents;
    }

}
