package codesquad.web;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions")
    public String qnaForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession httpSession) {
        User sessionedUser = (User) httpSession.getAttribute("sessionedUser");

        if(sessionedUser == null) {
            return "redirect:/login";
        }

        question.setWriter(sessionedUser);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{index}")
    public ModelAndView show(@PathVariable Long index, HttpSession httpSession) {
        ModelAndView mav = new ModelAndView("qna/show");
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        mav.addObject("question", savedQuestion);
        return mav;
    }

    @GetMapping("/questions/{index}/updateForm")
    public String updateForm(@PathVariable Long index, Model model, HttpSession httpSession) {
        User sessionedUser = (User) httpSession.getAttribute("sessionedUser");
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);

        if(!sessionedUser.isSameUser(savedQuestion.getWriter().getUserId())) {
            return "failed";
        }

        model.addAttribute("question", savedQuestion);
        return "qna/updateForm";
    }

    @PutMapping("/questions/{index}")
    public String update(@PathVariable Long index, Question updatedQuestion) {
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        if(savedQuestion.equalsIndex(index)) {
            savedQuestion.update(updatedQuestion);
            questionRepository.save(savedQuestion);
        }
        return "redirect:/";
    }

    @DeleteMapping("/questions/{index}")
    public String delete(@PathVariable Long index) {
        Question question = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        questionRepository.delete(question);
        return "redirect:/";
    }
}
