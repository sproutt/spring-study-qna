package codesquad.qua;

import codesquad.user.User;
import codesquad.user.UserRepository;
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

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/questions/form")
    public String createForm(HttpSession session) {
        Object sessionedUser = session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "/login";
        }

        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("question", questionRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/questions/{id}")
    public String qnaInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("question", getQuestionById(id));
        return "qna/show";
    }

    @Transactional
    @PutMapping("/questions/{id}")
    public String update(Question changedQuestion, @PathVariable("id") Long id, HttpSession session) {
        Object sessionedUser = session.getAttribute("sessionedUser");

        if (sessionedUser != null) {
            User user = (User) sessionedUser;
            Question savedQuestion = getQuestionById(id);
            User savedUser = userRepository.findByName(savedQuestion.getWriter())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

            logger.info("user: {}", user.getName());
            logger.info("question: {}", savedQuestion.getWriter());

            if (user.getId().equals(savedUser.getId())) {
                savedQuestion.update(changedQuestion);

                return "redirect:/";
            }
        }

        return "qna/show_failed";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String updateForm(Model model, @PathVariable("id") Long id, HttpSession session) {
        Object sessionedUser = session.getAttribute("sessionedUser");

        if (sessionedUser != null) {
            User user = (User) sessionedUser;
            Question savedQuestion = getQuestionById(id);
            User savedUser = userRepository.findByName(savedQuestion.getWriter())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

            logger.info("user: {}", user.getName());
            logger.info("question: {}", savedQuestion.getWriter());

            if (user.getId().equals(savedUser.getId())) {
                model.addAttribute("question", savedQuestion);
                return "qna/updateForm";
            }
        }

        return "qna/show_failed";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String remove(@PathVariable("id") Long id) {
        questionRepository.delete(getQuestionById(id));
        return "redirect:/";
    }

    private Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
