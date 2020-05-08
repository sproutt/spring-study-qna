package com.greenfrog.qna.controller;

import com.greenfrog.qna.domain.Question;
import com.greenfrog.qna.dto.QuestionUpdateDTO;
import com.greenfrog.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "/index";
    }

    @PostMapping("/questions")
    public String registerQuestion(@RequestBody Question question) {
        questionService.registerQuestion(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findQuestionById(id));
        return "/qna/show";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String showUpdateForm(@PathVariable int id, Model model) {
        model.addAttribute("question", questionService.findQuestionById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable int id, QuestionUpdateDTO questionUpdateDTO) {
        questionService.updateQuestion(id, questionUpdateDTO);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable int id) {
        questionService.deleteQuestion(id);
        return "redirect:/";
    }

}

