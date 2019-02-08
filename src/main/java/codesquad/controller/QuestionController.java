package codesquad.controller;

import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String questionForm(HttpSession session, Model model) {
        Object sessionedObject = session.getAttribute("sessionedUser");
        if (sessionedObject == null) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = (User) sessionedObject;
        model.addAttribute("user", sessionedUser);
        return "qna/form";
    }

    @PostMapping("/questions")
    public String quest(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public ModelAndView bringQuestionsList() {
        ModelAndView modelAndView = new ModelAndView("qna/list");
        modelAndView.addObject("questions", questionRepository
                .findAll());
        return modelAndView;
    }

    @GetMapping("/questions/{id}")
    public ModelAndView showQuestion(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("qna/show");
        modelAndView.addObject("question", questionRepository.findById(id).get());

        return modelAndView;
    }

    @GetMapping("/questions/{id}/updateForm")
    public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        Object sessionedObject = session.getAttribute("sessionedUser");
        User sessionedUser = (User) sessionedObject;

        if (!sessionedUser.getName().equals(questionRepository.findById(id).get().getWriter())) {
            return "/utils/authenticationError";
        }
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/updateForm";
    }


    @PutMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable Long id, Question question, HttpSession session) {
        Object sessionedObject = session.getAttribute("sessionedUser");
        User sessionedUser = (User) sessionedObject;
        if (sessionedObject == null) {
            return "redirect:/users/login";
        }
        if (!question.getWriter().equals(sessionedUser.getName())) {
            return "redirect:/users/login";
        }
        Question originalQustion = questionRepository.findById(id).get();
        originalQustion.update(question);
        questionRepository.save(questionRepository.findById(id).get());
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, Question question, HttpSession session) {
        Object sessionedObject = session.getAttribute("sessionedUser");
        User sessionedUser = (User)sessionedObject;
        if(sessionedObject == null){
            return "redirect:/users/login";
        }
        if(sessionedUser.getName().equals(questionRepository.
                findById(id).get().
                getWriter())){
            return "redirect:/users/login";
        }
        questionRepository.delete(questionRepository.findById(id).get());
        return "redirect:/";
    }
}
