package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.domain.WriteQuestionDto;
import codesquad.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions/form")
    public String showQuestionForm(HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
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
                                                  .orElseThrow(NoSuchElementException::new);
            model.addAttribute("question", question);
        } catch (NoSuchElementException exception) {
            return "qna/no_question_form";
        }
        return "qna/show";
    }

    @GetMapping("/questions/{id}/editor")
    public String showEditForm(@PathVariable Long id, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
            try {
                User sessionedUser = (User) sessionValue;
                questionRepository.findById(id)
                                  .filter(s -> s.getId()
                                                .equals(sessionedUser.getId()))
                                  .orElseThrow(NoSuchElementException::new);
            } catch (NoSuchElementException exception) {
                return "qna/no_access_form";
            }
            return "qna/edit_form";
        }
        return "qna/no_session_form";
    }

    @PostMapping("/questions")
    public String create(@ModelAttribute WriteQuestionDto writeQuestionDto, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (sessionValue != null) {
            Question question = new Question();
            User sessionedUser = (User) sessionValue;
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
        Object sessionValue = getSessionValue(session);
        if (getSessionValue(session) != null) {
            try {
                User sessionedUser = (User) sessionValue;
                Question question = questionRepository.findById(id)
                                                      .filter(s -> s.getId()
                                                                    .equals(sessionedUser.getId()))
                                                      .orElseThrow(NoSuchElementException::new
                                                      );

                question.write(sessionedUser, writeQuestionDto, getWrittenTime());
                questionRepository.save(question);
            } catch (NoSuchElementException exception) {
                return "qna/no_session_form";
            }
            return "redirect:/questions";
        }
        return "qna/no_access_form";
    }

    @DeleteMapping("/questions/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {
        Object sessionValue = getSessionValue(session);
        if (getSessionValue(session) != null) {
            try {
                User sessionedUser = (User) sessionValue;
                questionRepository.delete(questionRepository.findById(id)
                                                            .filter(s -> s.getId()
                                                                          .equals(sessionedUser.getId()))
                                                            .orElseThrow(NoSuchElementException::new));
            } catch (NoSuchElementException exception) {
                return "redirect:/users/login";
            }
        }
        return "redirect:/questions";
    }

    private Object getSessionValue(HttpSession session) {
        return session.getAttribute("sessionedUser");
    }
}
