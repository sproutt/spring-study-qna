package com.greenfrog.qna.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

    private String writer;
    private String title;
    private String contents;
    private String createTime;

}
