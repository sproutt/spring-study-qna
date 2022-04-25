package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
public class AnswerController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AnswerController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping("/questions/{question-id}/answers")
    @Transactional
    public String create(@PathVariable("question-id") Long questionId, String comment, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
            User user = (User) sessionValue;

            Answer answer = new Answer();
            answer.writeAnswer(comment, user.getName(), LocalDateTime.now());
            answerRepository.save(answer);

            Question question = questionRepository.findById(questionId)
                                                  .orElseThrow(NoSuchElementException::new);

            answer.addQuestion(question);

            return "redirect:/questions/" + questionId;
        }
        return "user/no_access_form";
    }

    @DeleteMapping("/questions/{question-id}/answers/{answer-id}")
    public String remove(@PathVariable("question-id") Long questionId, @PathVariable("answer-id") Long answerId, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
            User user = (User) sessionValue;

            Answer answer = answerRepository.findById(answerId)
                                            .filter(s -> s.getWriter()
                                                          .equals(user.getName()))
                                            .orElseThrow(NoSuchElementException::new);

            answerRepository.delete(answer);
            return "redirect:/questions/" + questionId;
        }

        return "user/no_access_form";
    }

    private Object getSessionValue(HttpSession session) {
        return session.getAttribute("sessionedUser");
    }
}
