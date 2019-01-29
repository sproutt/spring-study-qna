package codesquad.controller;

import codesquad.model.Question;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String questionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String quest(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public ModelAndView bringQuestionsList(Model model) {
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
    public ModelAndView questionUpdateForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("qna/updateForm");
        modelAndView.addObject("question", questionRepository.findById(id).get());
        return modelAndView;
    }


    @PutMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable Long id, Question question) {
        System.out.println("question update executed");
        questionRepository.findById(id).get()
                .setContents(question.getContents());
        questionRepository.findById(id).get()
                .setTitle(question.getTitle());
        questionRepository.findById(id).get()
                .setWriter(question.getWriter());
        questionRepository.save(questionRepository.findById(id).get());
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, Question question) {
        questionRepository.delete(questionRepository.findById(id).get());
        return "redirect:/";
    }
}
