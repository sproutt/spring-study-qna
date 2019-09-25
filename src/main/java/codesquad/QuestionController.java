package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questionList = new ArrayList<Question>();
    /*
    @GetMapping("/qna/form.html")
    public String getQuestion() {
        return "/qna/form";
    }
    */

    @PostMapping("/questions")
    public String question(Question question) {
        question.setIndex(questionList.size() + 1);
        String time = checkCurrentTime();
        question.setTime(time);
        questionList.add(question);
        System.out.println("add 후 question : " + question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionList);
        return "index";
    }

    @GetMapping("/questions/{index}")
    public String getQuestion(@PathVariable("index") int index, Model model) {
        for (Question question : questionList) {
            if (question.isSameIndex(index, question)) {
                model.addAttribute("question", question);
                break;
            }
        }
        return "qna/show";
    }

    private String checkCurrentTime() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + " " + time.format(today);
    }
}
