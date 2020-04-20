package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute( "questions",questionRepository.findAll());
        return "/index";
    }

    @PostMapping("/questions")
    public String registerQuestion(Question question) {
        questionRepository.insert(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String showQuestion(@PathVariable int index, Model model) {
        model.addAttribute("question", questionRepository.find(index));
        return "/qna/show";
    }


}

