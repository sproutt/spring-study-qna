package codesquad.web;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtil;
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
    public String qnaForm(HttpSession httpSession) {
        if (!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession httpSession) {
        if (!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

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
    public ModelAndView show(@PathVariable Long index) {
        ModelAndView mav = new ModelAndView("qna/show");
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        mav.addObject("question", savedQuestion);
        return mav;
    }

    @GetMapping("/questions/{index}/form")
    public String updateForm(@PathVariable Long index, Model model, HttpSession httpSession) {
        if (!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

        if (!savedQuestion.isSameWriter(sessionedUser)) {
            return "failed";
        }

        model.addAttribute("question", savedQuestion);

        return "qna/updateForm";
    }

    @PutMapping("/questions/{index}")
    public String update(@PathVariable Long index, Question updatedQuestion, HttpSession httpSession) {
        if (!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);

        if (!savedQuestion.isSameWriter(sessionedUser)) {
            return "failed";
        }

        savedQuestion.update(updatedQuestion);

        questionRepository.save(savedQuestion);

        return String.format("redirect:/questions/%d", index);
    }

    @DeleteMapping("/questions/{index}")
    public String delete(@PathVariable Long index, HttpSession httpSession) {
        if (!HttpSessionUtil.isLoginUser(httpSession)) {
            return "redirect:/users/login/form";
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
        Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);

        if (!savedQuestion.isSameWriter(sessionedUser)) {
            return "redirect:/users/login/form";
        }

        if(!savedQuestion.canDelete()) {
            throw new IllegalStateException("질문을 삭제할 수 없습니다.");
        }

        savedQuestion.delete();

        questionRepository.save(savedQuestion);

        return "redirect:/";
    }
}
