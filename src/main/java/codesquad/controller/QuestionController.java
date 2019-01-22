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

    private List<Question> questions = new ArrayList<>();

    @GetMapping("/qna/form")
    public String questionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String quest(Question question) {
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String bringQuestionsList(Model model){
        model.addAttribute("questions", questions);
        return "qna/list";
    }
    @GetMapping("/questions/{index}")
    public String showQuestion(Model model, @PathVariable int index) {
        for(int i=0;i<questions.size();i++){
            if(questions.get(i).getIndex() == index){
                model.addAttribute("question", questions.get(i));
                break;
            }
        }
        return "qna/show";
    }
}
