package com.greenfrog.qna.controller;

import com.greenfrog.qna.domain.Question;
import com.greenfrog.qna.dto.QuestionUpdateDTO;
import com.greenfrog.qna.repository.QuestionRepository;
import com.greenfrog.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "/index";
    }

    @PostMapping("/questions")
    public String registerQuestion(Question question) {
        questionService.register(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/show";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable Long id, QuestionUpdateDTO questionUpdateDTO) {
        questionService.update(id, questionUpdateDTO);
        return "redirect:/";
    }
}

