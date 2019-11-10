package codesquad.controller;

import codesquad.domain.Question;
import codesquad.service.QuestionService;
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
    public String question(Question question, HttpSession session) {
        return questionService.question(question, session);
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionService.findQuestionList());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String questionsShow(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        return questionService.updateQuestion(id, model, session);
    }

    @PutMapping("/questions/{id}")
    public String editQuestion(@PathVariable("id") Long id, Question newQuestion, Model model, HttpSession session) {
        return questionService.editQuestion(id, newQuestion, model, session);
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        return questionService.deleteQuestion(id, session);
    }

    @GetMapping("/questions/form")
    public String clickQuestion(HttpSession session) {
        return questionService.clickQuestion(session);
    }
}
