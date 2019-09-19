package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    /*
    @GetMapping("/qna/form.html")
    public String questionBtn(Question question){
        return "qna/form";
    }
    */
    @PostMapping("/questions")
    public String question(Question question){

        return "qna/form";
    }
}
