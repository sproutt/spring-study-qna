package codesquad.controller;

import codesquad.model.Question;
import codesquad.service.QuestionService;
import codesquad.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/questions")
    public String questions(Question question, HttpSession session) {
        questionService.saveQuestion(question, session);

        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionService.findQuestions());

        return "/qna/list";
    }

    @GetMapping("questions/form")
    public String getQuestionForm(HttpSession session, Model model) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("user", HttpSessionUtils.getSessionedUser(session));

        return "/qna/form";
    }

    @GetMapping("/questions/{questionId}")
    public String accessQuestion(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", questionService.findById(questionId));

        return "/qna/show";
    }

    @GetMapping("/questions/{questionId}/form")
    public String modifyQuestion(@PathVariable Long questionId, Model model, HttpSession session) {
        if (!questionService.isSameWriter(questionId, HttpSessionUtils.getSessionedUser(session))) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("question", questionService.findById(questionId));

        return "/qna/updateForm";
    }

    @PutMapping("/questions/{questionId}")
    public String updateQuestion(@PathVariable Long questionId, Question updatedQuestion) {
        questionService.updateQuestion(questionService.findById(questionId), updatedQuestion);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/questions/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId, HttpSession session) {
        if (!questionService.isSameWriter(questionService.findWriterIdByQuestionId(questionId), HttpSessionUtils.getSessionedUser(session))) {
            return "redirect:/users/loginForm";
        }
        questionService.deleteQuestion(questionId);

        return "redirect:/";
    }
}
