package codesquad.service;

import codesquad.model.Question;
import codesquad.repository.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void saveQuestion(Question question, HttpSession session) {
        question.setWriter(HttpSessionUtils.getSessionedUser(session));
        question.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        questionRepository.save(question);
    }

    public Iterable<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void updateQuestion(Question question, Question updatedQuestion) {
        question.update(updatedQuestion);
        question.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.delete(questionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
}
