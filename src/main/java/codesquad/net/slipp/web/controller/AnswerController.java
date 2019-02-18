package codesquad.net.slipp.web.controller;

import codesquad.net.slipp.web.service.AnswerService;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public String create(@PathVariable long questionId, HttpSession session, String content) {
        if(!SessionUtil.isLogin(session)){

            return "redirect:/users/login";
        }
        answerService.save(session, content, questionId);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long questionId, @PathVariable long id, HttpSession session) {
        answerService.delete(session, id);

        return "redirect:/questions/{questionId}";
    }
}
