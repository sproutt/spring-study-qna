package codesquad.web;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/questions/{index}/answers")
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long index, Answer answer, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
            Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

            answer.setWriter(sessionedUser);
            answer.setQuestion(savedQuestion);
            savedQuestion.addCountOfAnswer();

            answerRepository.save(answer);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        } catch (NoSuchElementException exception) {
            return "redirect:/";
        }

        return String.format("redirect:/questions/%d", index);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long index, @PathVariable Long id, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            Answer savedAnswer = answerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("답변이 존재하지 않습니다."));
            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

            if (!savedAnswer.isSameWriter(sessionedUser)) {
                throw new IllegalStateException("다른 사람의 게시글을 삭제할 수 없습니다.");
            }

            savedAnswer.delete();

            answerRepository.save(savedAnswer);

            Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
            savedQuestion.deleteAnswer();

            questionRepository.save(savedQuestion);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        } catch(NoSuchElementException exception) {
            return "redirect:/";
        }

        return String.format("redirect:/questions/{index}");
    }
}