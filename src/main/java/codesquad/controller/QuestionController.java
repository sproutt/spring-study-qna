package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.QuestionRepository;
import codesquad.util.HttpSessionUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "qna/index";
    }

    @GetMapping("/questions/form")
    public String show(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession session) {
        question.setUserInfo(session);

        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        if (!question.isSameUserId(sessionedUser)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PostMapping("/questions/{id}/update")
    public String update(@PathVariable Long id, Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question beforeQuestion = questionRepository.findById(id).get();
        if (!beforeQuestion.isSameUserId(sessionedUser)) {
            throw new IllegalStateException("You can't edit other's article");
        }
        beforeQuestion.changeQuestionInfo(question);
        questionRepository.save(beforeQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).get();
        if (!question.isSameUserId(sessionedUser)) {
            return "redirect:/users/loginForm";
        }
        questionRepository.deleteById(id);
        return "redirect:/";
    }

}
