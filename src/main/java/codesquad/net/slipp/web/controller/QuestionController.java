package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.service.QuestionService;
import codesquad.net.slipp.web.utils.ErrorMessageUtils;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping("")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "/qna/show";
    }

    @PostMapping("")
    public String createQuestion(Question question, HttpSession session) {
        question.setWriter(sessionUtil.getSessionUser(session));
        questionRepository.save(question);

        return "redirect:/questions";
    }

    @GetMapping("/form")
    public String getQuestionForm(HttpSession session) {
        sessionUtil.isLogin(session);

        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String getQuestionFormDetail(@PathVariable long id, Model model){
        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/showDetail";
    }

    @GetMapping("/{id}/form")
    public String getQuestionUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionService.findById(id);
        sessionUtil.isSessionMatch(session, question.getWriter());
        model.addAttribute("question", question);

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestionDetail(@PathVariable long id, Question updatedQuestion,
                                       HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Object value = session.getAttribute("userSession");
        if (value == null) {
            return "redirect:/users/login";
        }
        User userBySession = (User) value;
        if (!question.getWriter().equals(userBySession.getUserId())) {
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
