package com.greenfrog.qna.domain;

import com.greenfrog.qna.dto.QuestionUpdateDTO;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

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

    public Question() {

    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }


    public void update(QuestionUpdateDTO questionUpdateDTO) {
        this.writer = questionUpdateDTO.getWriter();
        this.title = questionUpdateDTO.getTitle();
        this.contents = questionUpdateDTO.getContents();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
