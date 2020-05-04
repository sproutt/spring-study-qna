package com.greenfrog.qna.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionUpdateDTO {
    private String writer;
    private String title;
    private String contents;
}
