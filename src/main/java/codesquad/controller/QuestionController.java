package codesquad.controller;

import codesquad.model.Question;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

//TODO 5. Use @Autowired annotation, make dependency relation with QuestionRepository and QuestionController
//TODO 6. Save Question data via QuestionRepository's save() method.

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

    //TODO 7. Use QuestionRepository's findAll() method, request the whole question list data.
    @GetMapping("/")
    public ModelAndView bringQuestionsList(Model model) {
        ModelAndView modelAndView = new ModelAndView("qna/list");
        modelAndView.addObject("questions", questionRepository
        .findAll());
        return modelAndView;
    }

    //TODO 8. As same as TODO 7, use findAll() method, and make show detail of question function.
    @GetMapping("/questions/{id}")
    public ModelAndView showQuestion(Model model, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("qna/show");
        modelAndView.addObject("question", questionRepository.findById(id).get());

        return modelAndView;
    }

    //TODO 9. Requirement : In the question detail showing view, it should make it possible update question.
    // it should be done by edit qna/form.html file to qna/updateForm.html file and some change6 the details.

    //TODO 10. Requirement : You can also delete question in the detail showing view.
    // use QuestionRepository's delete() method. When in you click the delete button in show.html, delete value should transmitted
    // such as hidden like "<input type='hidden' name='_method' value='DELETE'/>"
}
