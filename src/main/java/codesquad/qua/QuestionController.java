package codesquad.qua;

import codesquad.answer.Answer;
import codesquad.answer.AnswerRepository;
import codesquad.user.User;
import codesquad.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @GetMapping("/questions/form")
    public String createForm(HttpSession session) {
        User user = SessionUtil.getUserBySession(session);

        if (user == null) {
            return "/login";
        }

        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);

        if (user == null) {
            return "/login";
        }

        question.setWriter(user);
        questionRepository.save(question);
        logger.info("user : {}", question.getWriter().getName());
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("question", questionRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/questions/{id}")
    public String qnaInfo(Model model, @PathVariable("id") Long id) {
        Question question = findQuestionById(id);
        model.addAttribute("question", question);
        model.addAttribute("count", countAnswers(question));

        return "qna/show";
    }

    @Transactional
    @PutMapping("/questions/{id}")
    public String update(Question changedQuestion, @PathVariable("id") Long id, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);
        Question savedQuestion = findQuestionById(id);

        if (user == null || !isQuestionMatchUser(user, savedQuestion)) {
            return "qna/show_failed";
        }

        logger.info("user: {}", user.getName());
        logger.info("question: {}", savedQuestion.getWriter());

        savedQuestion.update(changedQuestion);

        return "redirect:/";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String updateForm(Model model, @PathVariable("id") Long id, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);
        Question savedQuestion = findQuestionById(id);

        if (user == null || !isQuestionMatchUser(user, savedQuestion)) {
            return "qna/show_failed";
        }

        logger.info("user: {}", user.getName());
        logger.info("question: {}", savedQuestion.getWriter());

        model.addAttribute("question", savedQuestion);

        return "qna/updateForm";
    }

    @DeleteMapping("/questions/{id}")
    @Transactional
    public String remove(@PathVariable("id") Long id, HttpSession session) {
        User user = SessionUtil.getUserBySession(session);
        Question savedQuestion = findQuestionById(id);

        logger.info("user: {}", user.getName());
        logger.info("question: {}", savedQuestion.getWriter());

        if (user == null || !isQuestionMatchUser(user, savedQuestion)) {
            return "qna/show_failed";
        }

        if (!canDeleteQuestion(savedQuestion, user)) {
            return "qna/delete_failed";
        }

        savedQuestion.changeDeleteFlag();

        for (Answer answer : savedQuestion.getAnswers()) {
            answer.changeDeletedFlag();
        }

        return "redirect:/questions/" + id;
    }

    private Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                                 .orElseThrow(NoSuchElementException::new);
    }

    private boolean isQuestionMatchUser(User loginUser, Question question) {
        return question.equalsWriter(loginUser);
    }

    private boolean canDeleteQuestion(Question deletedQuestion, User user) {
        if (deletedQuestion.hasAnswers()) {
            for (Answer answer : deletedQuestion.getAnswers()) {
                if (!answer.equalsWriter(user)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countAnswers(Question question) {
        int count = 0;
        for (Answer answer : question.getAnswers()) {
            if (!answer.isDeletedFlag()) {
                count++;
            }
        }
        return count;
    }
}