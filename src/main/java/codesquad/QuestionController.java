package codesquad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    //private List<Question> questionList = new ArrayList<Question>();

    @PostMapping("/questions")
    public String question(Question question) {
        //question.setIndex(questionList.size() + 1);
        String time = checkCurrentTime();
        question.setTime(time);
        questionRepository.save(question);
        System.out.println("add 후 question : " + question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public ModelAndView questionShow(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("question/profile");
        mav.addObject("question", questionRepository.findById(id).get());
        return mav;
    }

    /*
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
    */

    private String checkCurrentTime() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        return date.format(today) + " " + time.format(today);
    }
}
