package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.domain.WriteQuestionDto;
import codesquad.repository.AnswerRepository;
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
    private final AnswerRepository answerRepository;

    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
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
            model.addAttribute("count", question.getAnswers()
                                                .size());
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

                Question question = questionRepository.findById(id)
                                                      .orElseThrow(NoSuchElementException::new);

                if (question.getWriter()
                            .getId()
                            .equals(sessionedUser.getId())) {
                    if (question.getAnswers()
                                .size() == 0) {
                        question.setDeleteFlag(true);
                    } else {
                        boolean flag = true;
                        for (Answer answer : question.getAnswers()) {
                            if (!answer.getWriter()
                                       .equals(question.getWriter()
                                                       .getName())) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            answerRepository.deleteAll(question.getAnswers());
                            question.getAnswers()
                                    .clear();
                            question.setDeleteFlag(true);
                        }
                    }
                }
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
