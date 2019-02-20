package codesquad.controller;

import codesquad.model.answer.Answer;
import codesquad.model.question.Question;
import codesquad.model.user.User;
import codesquad.service.AnswerService;
import codesquad.service.QuestionService;
import codesquad.service.UserService;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String goToQnaForm(HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String goToQnaShow(Model model, @PathVariable Long id) {
        model.addAttribute("question", questionService.findById(id));
        model.addAttribute("answerSize", questionService.getAnswerSize(id));
        model.addAttribute("answers", questionService.getAnswer(id));
        return "/qna/show";
    }

    @PostMapping("/{userId}")
    public String createQuestion(Question question, @PathVariable String userId) {
        User writer = userService.findByUserId(userId);
        question.setWriter(writer);
        questionService.save(question);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String gotoUpdateForm(Model model, @PathVariable Long id, HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionService.findById(id);
        User user = userService.findSessionUser(session);
        questionService.isAuthority(question, user);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(Model model, @PathVariable Long id, HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return "redirect:/users/loginForm";
        }
        User user = userService.findSessionUser(session);
        questionService.delete(id, user);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateQuestion(Question newQuestion, @PathVariable Long id) {
        questionService.update(id, newQuestion);
        return "redirect:/";
    }

    @PostMapping("/{id}/answer")
    public String addAnswer(Answer answer, @PathVariable Long id, HttpSession session) {
        User user = userService.findSessionUser(session);
        if (user == null) {
            return "redirect:/users/loginForm";
        }
        answerService.save(user, answer, id);
        return "redirect:/question/" + id.toString();
    }

    @DeleteMapping("/{id}/answer/{answerId}")
    public String deleteAnswer(@PathVariable Long id, @PathVariable Long answerId, HttpSession session) {
        User user = userService.findSessionUser(session);
        if (user == null) {
            return "redirect:/users/loginForm";
        }
        answerService.delete(answerId, user);
        return "redirect:/question/" + id.toString();
    }

}
