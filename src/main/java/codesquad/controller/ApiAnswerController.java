package codesquad.controller;

import codesquad.exception.NullQuestionException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerDto;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.answer.ContentDto;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/question/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public AnswerDto create(@PathVariable Long questionId,@RequestBody ContentDto content, HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return null;
        }
        User loginUser = (User)session.getAttribute("user");
        Question question = questionRepository.findById(questionId).orElseThrow(()->new NullQuestionException(questionId));
        Answer answer = new Answer(loginUser, question , content.getContent());
        return new AnswerDto(answerRepository.save(answer));
    }

}