package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.service.QuestionService;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionService.findAll());

        return "/qna/show";
    }

    @PostMapping("")
    public String createQuestion(Question question, HttpSession session) {
        question.setWriter(SessionUtil.getSessionUser(session));
        questionService.save(question);

        return "redirect:/questions";
    }

    @GetMapping("/form")
    public String getQuestionForm(HttpSession session) {
        SessionUtil.isLogin(session);

        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String QuestionFormDetail(@PathVariable long id, Model model) {
        model.addAttribute("question", questionService.findById(id));

        return "/qna/showDetail";
    }

    @GetMapping("/{id}/form")
    public String getQuestionUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        model.addAttribute("question", questionService.isSessionMatch(session, id));

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestionDetail(@PathVariable long id, Question updatedQuestion, HttpSession session) {
        questionService.update(questionService.isSessionMatch(session, id), updatedQuestion);

        return "redirect:/questions";
    }

    @DeleteMapping("/{id}")
    public String get(@PathVariable long id) {
        questionService.deleteById(id);

        return "redirect:/questions";
    }

}
