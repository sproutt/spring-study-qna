package codesquad;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ModelAndView questionsShow(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("qna/show");
        mav.addObject("question", questionRepository.findById(id).get());
        return mav;
    }

    @GetMapping("/questions/{id}/form")
    public ModelAndView updateQuestionForm(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("qna/updateForm");
        mav.addObject("question", questionRepository.findById(id).get());
        return mav;
    }

    @PostMapping("/questions/{id}/update")
    public String editQuestion(@PathVariable("id") Long id, Question question){
        Question changedQuestion = questionRepository.findById(id).get();
        changedQuestion.setTitle(question.getTitle());
        changedQuestion.setContents(question.getContents());
        questionRepository.save(changedQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}/delete")
    public ModelAndView deleteQuestion(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("redirect:/");
        questionRepository.delete(questionRepository.findById(id).get());
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
