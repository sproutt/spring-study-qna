package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.utils.ErrorMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ErrorMessageUtils errorMessageUtils;

    @GetMapping("")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "/qna/show";
    }

    @PostMapping("")
    public String createQuestion(Question question, HttpSession session) {
        Object value = session.getAttribute("userSession");
        if (value == null) {
            return "redirect:/users/login";
        }
        User sessionUser = (User) value;
        question.setWriter(sessionUser);
        questionRepository.save(question);

        return "redirect:/questions";
    }

    @GetMapping("/form")
    public String getQuestionForm(HttpSession session) {
        Object value = session.getAttribute("userSession");
        if (value == null) {
            return "redirect:/users/login";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String getQuestionFormDetail(@PathVariable long id, Model model,
                                        @RequestParam(required = false, name = "errorMessage") String errorMessage) {
        model.addAttribute("question", questionRepository.findById(id).get());
        model.addAttribute("errorMessage", errorMessage);
        return "/qna/showDetail";
    }

    @GetMapping("/{id}/form")
    public String getQuestionUpdateForm(@PathVariable long id, Model model,
                                        HttpSession session, RedirectAttributes redirectAttributes) {
        Question question = questionRepository.findById(id).get();
        Object value = session.getAttribute("userSession");
        if (value == null) {
            redirectAttributes.addAttribute("errorMessage", errorMessageUtils.getGetNotLogin());
            return "redirect:/users/login";
        }
        User userBySession = (User) value;
        if (!question.getWriter().equals(userBySession.getUserId())) {
            redirectAttributes.addAttribute("errorMessage", errorMessageUtils.getGetNotMatchSession());
            return "redirect:/questions/" + id;
        }
        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestionDetail(@PathVariable long id, Question updatedQuestion,
                                       HttpSession session, RedirectAttributes redirectAttributes) {
        Question question = questionRepository.findById(id).get();
        Object value = session.getAttribute("userSession");
        if (value == null) {
            redirectAttributes.addAttribute("errorMessage", "로그인 후 사용 가능합니다.");

            return "redirect:/users/login";
        }
        User userBySession = (User) value;
        if (!question.getWriter().equals(userBySession.getUserId())) {
            redirectAttributes.addAttribute("errorMessage", "다른 사람의 글은 변경할 수 없습니다.");
            return "redirect:/questions/" + id;
        }
        question.setTitle(updatedQuestion.getTitle());
        question.setContents(updatedQuestion.getContents());
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @DeleteMapping(value = "/{id}")
    public String get(@PathVariable long id) {
        questionRepository.deleteById(id);
        return "redirect:/questions";
    }

}
