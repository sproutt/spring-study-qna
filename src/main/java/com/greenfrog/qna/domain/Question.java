package com.greenfrog.qna.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private String writer;
    private String title;
    private String contents;
    private String createTime;

}
