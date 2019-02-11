package codesquad.controller;

import codesquad.model.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("questions",questionRepository.findAll());
        return mav;
    }

}
