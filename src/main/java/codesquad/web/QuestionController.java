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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions")
    public String qnaForm(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Question question, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

            question.setWriter(sessionedUser);

            questionRepository.save(question);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        }

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
    public String updateForm(@PathVariable Long index, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

            if (!savedQuestion.isSameWriter(sessionedUser)) {
                throw new IllegalStateException("다른 사람의 게시글을 수정할 수 없습니다.");
            }

            model.addAttribute("question", savedQuestion);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        } catch (NoSuchElementException exception) {
            return "redirect:/";
        }

        return "qna/updateForm";
    }

    @PutMapping("/questions/{index}")
    public String update(@PathVariable Long index, Question updatedQuestion, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
            Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

            if (!savedQuestion.isSameWriter(sessionedUser)) {
                throw new IllegalStateException("다른 사람의 게시글을 수정할 수 없습니다.");
            }

            savedQuestion.update(updatedQuestion);

            questionRepository.save(savedQuestion);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        } catch (NoSuchElementException exception) {
            return "redirect:/";
        }

        return String.format("redirect:/questions/%d", index);
    }

    @DeleteMapping("/questions/{index}")
    public String delete(@PathVariable Long index, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
            Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

            if (!savedQuestion.isSameWriter(sessionedUser)) {
                throw new IllegalStateException("다른 사람의 게시글을 삭제할 수 없습니다.");
            }

            if (!savedQuestion.canDelete()) {
                throw new IllegalStateException("질문에 다른 사용자의 답변이 존재하여 질문을 삭제할 수 없습니다.");
            }

            savedQuestion.delete();

            questionRepository.save(savedQuestion);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        } catch (NoSuchElementException exception) {
            return "redirect:/";
        }

        return "redirect:/";
    }
}
