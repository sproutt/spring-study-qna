package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questionList = new ArrayList<Question>();

    @GetMapping("/qna/form.html")
    public String getQuestion() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String question(Question question) {
        System.out.println("question : "+ question);
        questionList.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model){
        model.addAttribute("questionList", questionList);
        return "index";
    }

}
