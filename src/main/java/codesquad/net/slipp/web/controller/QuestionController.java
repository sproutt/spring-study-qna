package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "/qna/show";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String PostQuestion(Question question) {
        questionRepository.save(question);

        return "redirect:/questions";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getQuestionForm() {

        return "/qna/form";
    }

    @RequestMapping(value = "/{id}")
    public String getQuestionFormDetail(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/showDetail";
    }
    @RequestMapping(value = "/{id}/form")
    public String getQuestionUpdateForm(@PathVariable long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());

        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestionDetail(@PathVariable long id, Question updatedQuestion) {
        Question question = questionRepository.findById(id).get();
        question.setTitle(updatedQuestion.getTitle());
        question.setWriter(updatedQuestion.getWriter());
        question.setContents(updatedQuestion.getContents());
        questionRepository.save(question);
        return "redirect:/questions";
    }
    @DeleteMapping(value = "/{id}")
    public String get(@PathVariable long id) {
        questionRepository.deleteById(id);
        return "redirect:/questions";
    }

}
