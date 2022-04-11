package codesquad.web;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/questions/{index}/answers")
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long index, Answer answer, HttpSession httpSession, Model model) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        answer.setWriter(sessionedUser);
        answer.setQuestion(savedQuestion);
        savedQuestion.addCountOfAnswer();
        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", index);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long index, @PathVariable Long id, HttpSession httpSession) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        Answer savedAnswer = answerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
        if(!savedAnswer.isSameWriter(sessionedUser)) {
            return "redirect:/users/login/form";
        }

        answerRepository.delete(savedAnswer);

        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        savedQuestion.deleteAnswer();
        questionRepository.save(savedQuestion);

        return String.format("redirect:/questions/{index}");
    }
}
