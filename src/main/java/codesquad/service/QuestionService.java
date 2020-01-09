package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public String findQuestions(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "index";
    }

    public String findQuestion(Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return "qna/show";
    }

    public String update(Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null && question.getWriter().isSameUserId((User) value)) {
            model.addAttribute("question", question);
            return "qna/updateForm";
        } else {
            model.addAttribute("userMissMatchQuestion", question);
            return "qna/show";
        }
    }

    public String edit(Long id, Question newQuestion, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null && question.getWriter().isSameUserId((User) value)) {
            question.changeInfo(newQuestion);
            questionRepository.save(question);
            return "redirect:/";
        } else {
            model.addAttribute("userMissMatchQuestion", question);
            return "qna/show";
        }
    }

    public String delete(Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null && question.getWriter().isSameUserId((User) value)) {
            if (answerRepository.findByQuestionId(id).isPresent()) {
                List<Answer> answers = answerRepository.findByQuestionId(id).orElseThrow(NullPointerException::new);
                for (int i = 0; i < answers.size(); i++) {
                    if (!answers.get(i).getWriter().getId().equals(question.getWriter().getId())) {
                        return "redirect:/questions/" + id;
                    }
                }
                questionRepository.delete(question);
                return "redirect:/";
            } else {
                questionRepository.delete(question);
                return "redirect:/";
            }
        } else {
            return "user/login_failed";
        }
    }

    public String click(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            return "qna/form";
        } else {
            return "/users/login";
        }
    }

    public String question(Question question, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User loginUser = (User) value;
            question.setCurrentTime();
            question.setUserInfo(loginUser);
            questionRepository.save(question);
            System.out.println("add 후 question : " + question);
            return "redirect:/";
        } else {
            return "redirect:/users/login";
        }
    }
}
