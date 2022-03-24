package codesquad.qua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("question", questionRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/questions/{id}")
    public String qnaInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @PostMapping("/questions/{id}/update")
    public String update(Question question, @PathVariable("id") Long id) {
        Question findQuestion = questionRepository.findById(id).get();

        findQuestion.update(question);
        questionRepository.save(findQuestion);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        Question findQuestion = questionRepository.findById(id).get();
        model.addAttribute("question", findQuestion);

        return "/qna/updateForm";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String remove(@PathVariable("id") Long id) {
        questionRepository.delete(questionRepository.findById(id).get());
        return "redirect:/";
    }
}
