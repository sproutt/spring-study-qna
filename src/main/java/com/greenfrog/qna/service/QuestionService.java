package com.greenfrog.qna.service;

import com.greenfrog.qna.domain.Question;
import com.greenfrog.qna.dto.QuestionDTO;
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

    public void registerQuestion(QuestionDTO questionDTO) {
        questionRepository.save(new Question(questionDTO));
    }

    public Question findQuestionById(int id) {
        return questionRepository.findById((long) id).orElseThrow(NoSuchElementException::new);
    }


    public void updateQuestion(int id, QuestionDTO questionDTO) {
        Question question = findQuestionById(id);
        question.update(questionDTO);
        questionRepository.save(question);
    }

    public void deleteQuestion(int id) {
        questionRepository.deleteById((long) id);
    }
}
