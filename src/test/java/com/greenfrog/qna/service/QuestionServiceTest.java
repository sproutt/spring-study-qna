package com.greenfrog.qna.service;

import com.greenfrog.qna.domain.Question;
import com.greenfrog.qna.dto.QuestionUpdateDTO;
import com.greenfrog.qna.repository.QuestionRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class QuestionServiceTest {

    QuestionRepository questionRepository;

    QuestionService questionService;

    @Before
    public void setUp() {
        questionRepository = Mockito.mock(QuestionRepository.class);
        questionService = new QuestionService(questionRepository);
    }

    @Test
    public void updateTest() {
        QuestionUpdateDTO questionUpdateDTO = new QuestionUpdateDTO();
        questionUpdateDTO.setTitle("Javascript");
        questionUpdateDTO.setWriter("Gipyoo");
        questionUpdateDTO.setContents("Gipyoo likes Javascript");

        Question question = new Question();
        question.setId(1l);
        question.setTitle("Java");
        question.setWriter("Jongjin");
        question.setContents("Jongjin likes Java");

        //given
        given(questionRepository.findById(1l)).willReturn(Optional.of(question));

        //when
        questionService.update(1l, questionUpdateDTO);

        //then
        assertThat(question.getTitle()).isEqualTo("Javascript");
        assertThat(question.getWriter()).isEqualTo("Gipyoo");
        assertThat(question.getContents()).isEqualTo("Gipyoo likes Javascript");
    }


}