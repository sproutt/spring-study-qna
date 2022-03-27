package codesquad.web;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions")
    public String qnaForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{index}")
    public ModelAndView show(@PathVariable Long index) {
        ModelAndView mav = new ModelAndView("qna/show");
        mav.addObject("question", questionRepository.findById(index).get());
        return mav;
    }

    @GetMapping("/questions/{index}/updateForm")
    public String updateForm(@PathVariable Long index, Model model) {
        model.addAttribute("question", questionRepository.findById(index).get());
        return "qna/updateForm";
    }

    @PostMapping("/questions/{index}")
    public String update(@PathVariable Long index, Question updatedQuestion) {
        Question savedQuestion = questionRepository.findById(index).orElseThrow(RuntimeException::new);
        if(savedQuestion.equalsIndex(index)) {
            savedQuestion.update(updatedQuestion);
            questionRepository.save(savedQuestion);
        }
        return "redirect:/";
    }

    @DeleteMapping("/questions/{index}")
    public String delete(@PathVariable Long index) {
        Question question = questionRepository.findById(index).orElseThrow(RuntimeException::new);
        questionRepository.delete(question);
        return "redirect:/";
    }
}
