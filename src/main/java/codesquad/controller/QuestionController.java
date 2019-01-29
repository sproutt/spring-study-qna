package codesquad.controller;

import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;


    @GetMapping("/qna/form")
    public String returnQnaForm(){
        return "/qna/form";
    }

    @PostMapping("/question")
    public String createQuestion(Question question){
        questionRepository.save(question);
        return"redirect:/";
    }

    @GetMapping("/")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("questions",questionRepository.findAll());
        return mav;
    }

    @GetMapping("/questions/{id}")
    public ModelAndView show(@PathVariable long id){
        ModelAndView mav = new ModelAndView("/qna/show");
        mav.addObject("question",questionRepository.findById(id).get());
        return mav;
    }


}
