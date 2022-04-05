package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.domain.WriteQuestionDto;
import codesquad.exception.NoSuchQuestionException;
import codesquad.exception.NoSuchUserException;
import codesquad.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions/form")
    public String showQuestionForm(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            return "qna/form";
        }
        return "qna/no_session_form";
    }

    @GetMapping("/questions")
    public String showAllQuestions(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String showQuestionDetail(@PathVariable Long id, Model model) {
        try {
            Question question = questionRepository.findById(id)
                                                  .orElseThrow(NoSuchQuestionException::new);
            model.addAttribute("question", question);
        } catch (NoSuchQuestionException exception) {
            return "qna/no_question_form";
        }
        return "qna/show";
    }

    @GetMapping("/questions/{id}/editor")
    public String showEditForm() {
        return "qna/edit_form";
    }

    @PostMapping("/questions")
    public String create(@ModelAttribute WriteQuestionDto writeQuestionDto, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            Question question = new Question();
            User sessionedUser = (User) value;
            question.write(sessionedUser, writeQuestionDto, getWrittenTime());
            questionRepository.save(question);
            return "redirect:/questions";
        }
        return "qna/no_session_form";
    }

    private LocalDateTime getWrittenTime() {
        return LocalDateTime.now();
    }

    @PutMapping("/questions/{id}")
    public String edit(@PathVariable Long id, WriteQuestionDto writeQuestionDto, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        // 작성 부분으로 이동시키고 작성해서 다시 돌아오게 만들어야함
        if (value != null) {
            try {
                User sessionedUser = (User) value;
                Question question = questionRepository.findById(id)
                                                      .filter(s -> s.getId()
                                                                    .equals(sessionedUser.getId()))
                                                      .orElseThrow(NoSuchUserException::new
                                                      );

                question.write(sessionedUser, writeQuestionDto, getWrittenTime());
                questionRepository.save(question);
            } catch (NoSuchUserException exception) {
                return "qna/no_access_form";
            }
        }
        return "redirect:/questions";
    }

    @DeleteMapping("/questions/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            try {
                User sessionedUser = (User) value;
                questionRepository.delete(questionRepository.findById(id)
                                                            .filter(s -> s.getId()
                                                                          .equals(sessionedUser.getId()))
                                                            .orElseThrow(NoSuchQuestionException::new));
            } catch (NoSuchQuestionException exception) {
                return "redirect:/users/login";
            }
        }
        return "redirect:/questions";
    }
}
