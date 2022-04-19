package codesquad.qua;

import codesquad.exception.QuestionDeleteException;
import codesquad.exception.QuestionShowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions/form")
    public String createForm(HttpSession session) {
        if (!questionService.setCreateForm(session)) {
            return "/login";
        }

        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(QuestionDto questionDto, HttpSession session) {
        if (!questionService.create(questionDto, session)) {
            return "/login";
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("question", questionService.list());
        return "qna/list";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(Model model, @PathVariable("id") Long id) {
        model.addAttribute("question", questionService.findQuestionById(id));
        model.addAttribute("count", questionService.countAnswers(id));
        return "qna/show";
    }

    @Transactional
    @PutMapping("/questions/{id}")
    public String updateQuestion(Question changedQuestion, @PathVariable("id") Long id, HttpSession session) {
        try {
            questionService.update(changedQuestion, id, session);
        } catch (QuestionShowException showException) {
            return "qna/show_failed";
        }

        return "redirect:/";
    }

    @GetMapping("/questions/{id}/updateForm")
    public String updateForm(Model model, @PathVariable("id") Long id, HttpSession session) {
        try {
            questionService.setUpdateForm(id, session);
        } catch (QuestionShowException showException) {
            return "qna/show_failed";
        }

        model.addAttribute("question", questionService.findQuestionById(id));

        return "qna/updateForm";
    }

    @DeleteMapping("/questions/{id}")
    @Transactional
    public String removeQuestion(@PathVariable("id") Long id, HttpSession session) {

        try {
            questionService.remove(id, session);
        } catch (QuestionShowException showException) {
            return "qna/show_failed";
        } catch (QuestionDeleteException deleteException) {
            return "qna/delete_failed";
        }

        return "redirect:/questions/" + id;
    }
}