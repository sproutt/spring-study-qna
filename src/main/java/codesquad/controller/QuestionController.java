package codesquad.controller;

import codesquad.QuestionRepository;
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
        question.checkCurrentTime();

        Object value = session.getAttribute("sessionedUser");
        User loginUser = (User) value;

        question.checkWriter(loginUser);

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

    @PutMapping("/questions/{id}/form")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            Object value = session.getAttribute("sessionedUser");
            User user = (User) value;
            if (value != null && findQuestion.get().getWriter().equals(user.getName())) {
                model.addAttribute("question", findQuestion.get());
                return "qna/updateForm";
            } else {
                return "/questions/{id}/missMatch";
            }
        } else {
            return "/questions/{id}";
        }
        /*
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            Object value = session.getAttribute("sessionedUser");
            User user = (User) value;
            if (value != null && findQuestion.get().getWriter().equals(user.getName())) {
                model.addAttribute("question", findQuestion.get());
                return "qna/updateForm";
            } else {
                return "/questions/{id}/missMatch";
            }
        } else {
            return "/questions/{id}";
        }
        */
        /*
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            Object value = session.getAttribute("sessionedUser");
            if (value == null) {
                return "/questions/{id}/missMatch";
            } else {
                User user = (User) value;
                if (findQuestion.get().getWriter().equals(user.getName())) {
                    model.addAttribute("question", findQuestion.get());
                    return "qna/updateForm";
                } else {
                    return "/questions/{id}/missMatch";
                }
            }
        } else {
            return "/questions/{id}";
        }
        */
    }


    /*
    @GetMapping("/missMatch")
    public String failed_list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/qna/show_failed_edit";
    }
    */


    @PostMapping("/questions/{id}/update")
    public String editQuestion(@PathVariable("id") Long id, Question question) {
        Question changedQuestion = questionRepository.findById(id).get();
        changedQuestion.setTitle(question.getTitle());
        changedQuestion.setContents(question.getContents());
        questionRepository.save(changedQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            questionRepository.delete(findQuestion.get());
            return "redirect:/";
        } else {
            return "/";
        }
    }
}
