package com.greenfrog.qna.domain;

import com.greenfrog.qna.dto.QuestionDTO;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private String createTime;

    public Question() {
    }

    public Question(QuestionDTO questionDTO) {
        this.writer = questionDTO.getWriter();
        this.title = questionDTO.getTitle();
        this.contents = questionDTO.getContents();
        createTime = new Date().toString();
    }

    public void update(QuestionDTO questionDTO) {
        this.writer = questionDTO.getWriter();
        this.title = questionDTO.getTitle();
        this.contents = questionDTO.getContents();
    }
}
