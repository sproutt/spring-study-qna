package codesquad.controller;

import codesquad.exception.NullQuestionException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.answer.ContentDto;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/question/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    @PostMapping("")
    public Answer create(@PathVariable Long questionId,@RequestBody ContentDto content, HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return null;
        }
        User loginUser = (User)session.getAttribute("user");
        Question question = questionRepository.findById(questionId).orElseThrow(()->new NullQuestionException(questionId));
        Answer answer = new Answer(loginUser, question , content.getContent());
        //Answer check =answerRepository.save(answer);
        return answerRepository.save(answer);
//        System.out.println(check.getContent()+"끄나풀");
//        System.out.println(check.getWriter().getName()+"끄나풀2");
//        System.out.println(check.getQuestion().getTitle()+"끄나풀3");
    }
}