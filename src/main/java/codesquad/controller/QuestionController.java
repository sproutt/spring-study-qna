package codesquad.controller;

import codesquad.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();

    @GetMapping("/qna/form")
    public String questionForm(){
        return "qna/form";
    }

    //TODO : 사용자가 전달한 값을 Question 클래스를 생성해 저장한다.
    @PostMapping("/questions")
    public String quest(){

        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String showQuestion(){

        return "qna/show";
    }
}
