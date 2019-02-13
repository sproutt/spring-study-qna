package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.WriterNotEqualException;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;

    public Question save(Question question, HttpSession httpSession) {
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        question.setWriter(user);
        question.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public void delete(Long id) {
        Question question = this.findById(id);
        if (this.getAnswerCount(id) != 0) {
            checkWriter(question);
        }
        question.setDeleted(true);
        answerService.delete(id);

        questionRepository.save(question);
    }

    public void update(Long id, Question modifiedQuestion) {
        Question question = this.findById(id);
        question.update(modifiedQuestion);
        questionRepository.save(question);
    }

    public boolean match(Long id, HttpSession httpSession) {
        return HttpSessionUtils.getSessionedUser(httpSession).equals(this.findById(id).getWriter());
    }

    public int getAnswerCount(Long id) {
        return this.findById(id).getAnswers().size();
    }

    public boolean isSameWriter(Question question, List<Answer> answers) {
        User writer = question.getWriter();
        for (Answer answer : answers) {
            if (!writer.equals(answer.getWriter())) {
                return false;
            }
        }
        return true;
    }

    public void checkWriter(Question question) {
        if (!isSameWriter(question, question.getAnswers())) {
            throw new WriterNotEqualException();
        }
    }
}
