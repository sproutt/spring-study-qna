package com.greenfrog.qna.domain;

import com.greenfrog.qna.dto.QuestionUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createTime;

    public Question() {
        createTime = new Date().toString();
    }

    public void update(QuestionUpdateDTO questionUpdateDTO) {
        this.writer = questionUpdateDTO.getWriter();
        this.title = questionUpdateDTO.getTitle();
        this.contents = questionUpdateDTO.getContents();
    }
}
