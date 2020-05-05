package com.greenfrog.qna.service;

import com.greenfrog.qna.domain.Question;
import com.greenfrog.qna.dto.QuestionUpdateDTO;
import com.greenfrog.qna.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void registerQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }


    public void updateQuestion(Long id, QuestionUpdateDTO questionUpdateDTO) {
        Question question = findQuestionById(id);
        question.update(questionUpdateDTO);
        questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
