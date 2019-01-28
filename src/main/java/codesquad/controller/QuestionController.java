package codesquad.controller;

import codesquad.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


//TODO 5. Use @Autowired annotation, make dependency relation with QuestionRepository and QuestionController
//TODO 6. Save Question data via QuestionRepository's save() method.

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

    //TODO 7. Use QuestionRepository's findAll() method, request the whole question list data.
    @GetMapping("/")
    public String bringQuestionsList(Model model) {
        model.addAttribute("questions", questions);
        return "qna/list";
    }
    //TODO 8. As same as TODO 7, use findAll() method, and make show detail of question function.
    @GetMapping("/questions/{index}")
    public String showQuestion(Model model, @PathVariable int index) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getIndex() == index) {
                model.addAttribute("question", questions.get(i));
                break;
            }
        }
        return "qna/show";
    }

    //TODO 9. Requirement : In the question detail showing view, it should make it possible update question.
    // it should be done by edit qna/form.html file to qna/updateForm.html file and some change the details.

    //TODO 10. Requirement : You can also delete question in the detail showing view.
    // use QuestionRepository's delete() method. When in you click the delete button in show.html, delete value should transmitted\
    // such as hidden like "<input type='hidden' name='_method' value='DELETE'/>"
}
