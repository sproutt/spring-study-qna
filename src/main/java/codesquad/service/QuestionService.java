package codesquad.service;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.exception.QuestionNotFoundException;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question, HttpSession httpSession) {
        User user = HttpSessionUtils.getSessionedUser(httpSession);

        question.setWriter(user);
        question.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public void deleteById(Long id) {
        questionRepository.delete(findById(id));
    }

    public void updateById(Long id, Question modifiedQuestion) {
        Question question = findById(id);
        question.update(modifiedQuestion);
        questionRepository.save(question);
    }
}
