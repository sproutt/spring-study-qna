package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String replyAnswer(@PathVariable("questionId") Long id, Answer answer, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User loginUser = (User) value;
            answer.setCurrentTime();
            answer.setUserInfo(loginUser);
            answer.setQuestionInfo(question);
            answerRepository.save(answer);
            System.out.println("객체생성");
            return "redirect:/questions/" + id;
        } else {
            return "redirect:/users/login";

        }
    }

    @DeleteMapping("/questions/{questionId}/answers/{id}")
    public String deleteAnswer(@PathVariable("questionId") Long questionId, @PathVariable("id") Long id, HttpSession session) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId).orElseThrow(NullPointerException::new);
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getId() == id) {
                Answer answer = answers.get(i);
                Object value = session.getAttribute("sessionedUser");
                if (value != null && answer.isSameUser((User) value)) {
                    answerRepository.delete(answer);
                    answers.remove(i);
                    return "redirect:/questions/" + questionId;
                } else {
                    return "redirect:/users/login";
                }
            }
        }
        return "redirect:/";
    }
}
