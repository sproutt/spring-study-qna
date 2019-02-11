package codesquad.controller;

import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import codesquad.utils.RepositoryUtil;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String goToQnaForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String goToQnaShow(Model model, @PathVariable Long id) {
        Optional<Question> question = RepositoryUtil.findQuestion(id, questionRepository);
        if (!question.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("question", question.get());
        return "/qna/show";
    }

    @PostMapping("/{userId}")
    public String createQuestion(Question question, @PathVariable String userId) {
        Optional<User> writer = RepositoryUtil.findUser(userId, userRepository);
        if (!writer.isPresent()) {
            return "redirect:/users/loginForm";
        }
        question.setWriter(writer.get());
        questionRepository.save(question);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String gotoUpdateForm(Model model, @PathVariable Long id, HttpSession session) throws Exception {
        Optional<User> user = SessionUtil.getSessionUser(session);
        if (!user.isPresent()) {
            return "redirect:/users/loginForm";
        }
        Optional<Question> question = RepositoryUtil.findQuestion(id, questionRepository);
        if (!user.get().getId().equals(question.get().getWriter().getId())) {
            model.addAttribute(question);
            return "/qna/showFail";
        }
        model.addAttribute(question);
        return "/qna/updateForm";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(Model model, @PathVariable Long id, HttpSession session) throws Exception {
        Optional<User> user = SessionUtil.getSessionUser(session);
        if (!user.isPresent()) {
            return "redirect:/users/loginForm";
        }
        Optional<Question> question = RepositoryUtil.findQuestion(id, questionRepository);
        if (!user.get().getId().equals(question.get().getWriter().getId())) {
            model.addAttribute(question.get());
            return "/qna/showFail";
        }
        questionRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateQuestion(Question newQuestion, @PathVariable Long id) {
        Optional<Question> question = RepositoryUtil.findQuestion(id, questionRepository);
        question.get().update(newQuestion);
        questionRepository.save(question.get());
        return "redirect:/";
    }

}
