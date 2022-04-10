package codesquad.answer;

import codesquad.qua.Question;
import codesquad.qua.QuestionRepository;
import codesquad.user.User;
import codesquad.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/questions/{question-id}/answers")
    public String create(@PathVariable("question-id") Long questionId, Answer answer, HttpSession session, Model model) {
        Question question = questionRepository.findById(questionId).orElseThrow(NoSuchElementException::new);

        logger.info("답글 내용 : {}", answer.getComment());
        logger.info("답글 작성자 : {}", answer.getWriter());

        User user = SessionUtil.getUserBySession(session);

        answer.addQuestion(question);
        answerRepository.save(answer);

        return "redirect:/questions/" + questionId;
    }
}