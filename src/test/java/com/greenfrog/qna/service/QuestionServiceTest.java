package com.greenfrog.qna.service;

import com.greenfrog.qna.domain.Question;
import com.greenfrog.qna.dto.QuestionDTO;
import com.greenfrog.qna.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class QuestionServiceTest {

    QuestionRepository questionRepository;

    QuestionService questionService;

    @Before
    public void setUp() {
        questionRepository = Mockito.mock(QuestionRepository.class);
        questionService = new QuestionService(questionRepository);
    }

    @Test
    public void updateQuestionTest() {
        QuestionDTO questionUpdateDTO = new QuestionDTO();
        questionUpdateDTO.setTitle("Javascript");
        questionUpdateDTO.setWriter("Gipyoo");
        questionUpdateDTO.setContents("Gipyoo likes Javascript");

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTitle("Java");
        questionDTO.setWriter("Jongjin");
        questionDTO.setContents("Jongjin likes Java");

        Question question = new Question(questionDTO);

        //given
        given(questionRepository.findById(1l)).willReturn(Optional.of(question));

        //when
        questionService.updateQuestion(1, questionUpdateDTO);

        //then
        assertThat(question.getTitle()).isEqualTo("Javascript");
        assertThat(question.getWriter()).isEqualTo("Gipyoo");
        assertThat(question.getContents()).isEqualTo("Gipyoo likes Javascript");
    }


}