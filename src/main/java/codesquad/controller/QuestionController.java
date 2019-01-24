package codesquad.controller;

import codesquad.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    List<Question> questions = new ArrayList<Question>();


    @GetMapping("/qna/form")
    public String returnQnaForm(){
        return "/qna/form";
    }

    @PostMapping("/question")
    public String createQuestion(Question question){
        questions.add(question);
        return"redirect:/";
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("questions",questions);
        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String profile(@PathVariable String index, Model model){
        model.addAttribute("question",questions.get(Integer.parseInt(index)));
        return "/qna/show";
    }


}
