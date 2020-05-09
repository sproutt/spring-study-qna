package com.greenfrog.qna.domain;

import com.greenfrog.qna.dto.QuestionUpdateDTO;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createTime;

    public void update(QuestionUpdateDTO questionUpdateDTO) {
        this.writer = questionUpdateDTO.getWriter();
        this.title = questionUpdateDTO.getTitle();
        this.contents = questionUpdateDTO.getContents();
    }

}
