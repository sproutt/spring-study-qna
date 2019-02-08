package codesquad.controller;

import codesquad.model.Question;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/qna/form")
    public String questionForm(HttpSession session) {
        //TODO 1. 로그인 사용자만 질문을 할 수 있도록 구
        //TODO 3. 로그인 하지 않은 사용자는 로그인페이지로 이동.
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
        //TODO 4. 글쓴이 필드에 User name값이 들어가도록 설정한다.
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
