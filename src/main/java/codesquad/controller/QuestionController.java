package codesquad.controller;

import codesquad.repository.QuestionRepository;
import codesquad.domain.Question;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String question(Question question, HttpSession session) {
        question.setCurrentTime();

        Object value = session.getAttribute("sessionedUser");
        User loginUser = (User) value;

        question.setUserInfo(loginUser);

        questionRepository.save(question);
        System.out.println("add 후 question : " + question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String questionsShow(@PathVariable("id") Long id, Model model) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            model.addAttribute("question", findQuestion.get());
            return "qna/show";
        } else {
            return "/";
        }
    }

    @GetMapping("/questions/{id}/missMatch")
    public String questionsShow_Failed_Edit(@PathVariable("id") Long id, Model model) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            model.addAttribute("question", findQuestion.get());
            return "/qna/show_failed_edit";
        } else {
            return "/";
        }
    }

    @GetMapping("/questions/{id}/form")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            Object value = session.getAttribute("sessionedUser");
            User user = (User) value;
            if (value != null && findQuestion.get().getWriter().isSameUserId(user)) {
                model.addAttribute("question", findQuestion.get());
                System.out.println("실행됐다.");
                return "qna/updateForm";
            } else {
                return "/questions/" + id + "/missMatch";
            }
        } else {
            return "/questions/" + id;
        }
    }

    @PutMapping("/questions/{id}/update")
    public String editQuestion(@PathVariable("id") Long id, Question question, HttpSession session) {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        if (maybeQuestion.isPresent()) {
            Object value = session.getAttribute("sessionedUser");
            User user = (User) value;
            if (value != null && maybeQuestion.get().getWriter().isSameUserId(user)) {
                maybeQuestion.get().changeQuestionInfo(question);
                questionRepository.save(maybeQuestion.get());
                return "redirect:/";
            } else {
                return "redirect:/questions/" + id + "/missMatch";
            }
        } else {
            return "redirect:/questions/" + id;
        }
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            Object value = session.getAttribute("sessionedUser");
            User user = (User) value;
            if (value != null && findQuestion.get().getWriter().isSameUserId(user)) {
                questionRepository.delete(findQuestion.get());
                return "redirect:/";
            } else {
                return "redirect:/users/login";
            }
        } else {
            return "/";
        }
    }

    @GetMapping("/questions/form")
    public String doQuestion(HttpSession session){
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            return "/qna/form";
        } else {
            return "/users/login";
        }

    }
}
