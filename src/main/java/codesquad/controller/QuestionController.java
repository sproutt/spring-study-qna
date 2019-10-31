package codesquad.controller;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;


    @PostMapping("/questions")
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

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String questionsShow(@PathVariable("id") Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model, HttpSession session) {
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

    @PutMapping("/questions/{id}")
    public String editQuestion(@PathVariable("id") Long id, Question newQuestion, Model model, HttpSession session) {
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

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null && question.getWriter().isSameUserId((User) value)) {
            if (answerRepository.findByQuestionId(id).isPresent()) {
                List<Answer> answers = answerRepository.findByQuestionId(id).orElseThrow(NullPointerException::new);
                for (int i = 0; i < answers.size(); i++) {
                    if(!answers.get(i).getWriterId().equals(question.getWriter().getId())){
                        return "redirect:/questions/"+id;
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

    @GetMapping("/questions/form")
    public String doQuestion(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            return "qna/form";
        } else {
            return "/users/login";
        }
    }
}
