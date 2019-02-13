package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.exception.AnswerNotFoundException;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    public Answer register(Long questionId, Answer answer, HttpSession httpSession) {
        answer.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        answer.setWriter(HttpSessionUtils.getSessionedUser(httpSession));

        questionService.findById(questionId).addAnswer(answer);

        return answerRepository.save(answer);
    }

    public void delete(Long questionId) {
        List<Answer> answers = this.findByQuestionId(questionId);
        Question question = questionService.findById(questionId);

        for (Answer answer : answers) {
        question.removeAnswer(answer);
        answer.setDeleted(true);
        answerRepository.save(answer);
        }
    }

    public void delete(Long questionId, Long id) {
        Answer answer = this.findById(id);
        questionService.findById(questionId).removeAnswer(answer);
        answer.setDeleted(true);
        answerRepository.save(answer);
    }

    private List<Answer> findByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId).orElseThrow(() -> new AnswerNotFoundException(questionId));
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
    }

    public boolean match(Long id, HttpSession httpSession) {
        return HttpSessionUtils.getSessionedUser(httpSession).equals(this.findById(id).getWriter());
    }
}
