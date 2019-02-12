package codesquad.controller;

import codesquad.model.question.QuestionRepository;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public String list(Model model){
        model.addAttribute("questions",questionService.findAll());
        return "/index";
    }

}
