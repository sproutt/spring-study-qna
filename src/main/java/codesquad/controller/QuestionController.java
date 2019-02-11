package codesquad.controller;

import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/qna/form")
    public String returnQnaForm(HttpSession session){
        Optional<User> user = (Optional<User>) session.getAttribute("user");
        if(user==null){
            return"redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("/qna/update/{id}")
    public String qnaUpdate(Question newQuestion,@PathVariable Long id){
        Question question = questionRepository.findById(id).get();
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @PostMapping("/question/{userId}")
    public String createQuestion(Question question, @PathVariable String userId){
        question.setWriter(userRepository.findByUserId(userId));
        questionRepository.save(question);
        return"redirect:/";
    }

    @PutMapping("/question/update/{id}")
    public String updateQuestion(Model model, @PathVariable Long id, HttpSession session)throws Exception{
        User user = (User)session.getAttribute("user");
        if(user == null){
            return"redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).get();
        if(!user.getId().equals(question.getWriter().getId())){
            model.addAttribute(question);
            return"/qna/showFail";
        }
        model.addAttribute(question);
        return"/qna/updateForm";
    }

    @DeleteMapping("/question/delete/{id}")
    public String deleteQuestion(Model model, @PathVariable Long id,HttpSession session)throws Exception{
        User user = (User)session.getAttribute("user");
        if(user == null){
            return"redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(id).get();
        if(!user.getId().equals(question.getWriter().getId())){
            model.addAttribute(question);
            return"/qna/showFail";
        }
        questionRepository.deleteById(id);
        return"redirect:/";
    }

    @GetMapping("/")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("questions",questionRepository.findAll());
        return mav;
    }

    @GetMapping("/questions/{id}")
    public ModelAndView show(@PathVariable long id){
        ModelAndView mav = new ModelAndView("/qna/show");
        mav.addObject("question",questionRepository.findById(id).get());
        return mav;
    }

}
