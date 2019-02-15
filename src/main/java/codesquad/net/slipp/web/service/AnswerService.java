package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.Answer;
import codesquad.net.slipp.web.domain.AnswerRepository;
import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.exception.AnswerNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotMatchException;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    public void save(HttpSession session, Answer answer, long questionId) {
        answer.setInfo(questionService.findById(questionId), SessionUtil.getSessionUser(session));
        answerRepository.save(answer);
    }

    public void delete(HttpSession session, long id) {
        SessionUtil.checkAuth(session, this.findById(id).getWriter());
        this.delete(id);
    }

    public Answer delete(Long id) {
        Answer answer = this.findById(id);
        answer.setDeleted(true);

        return answer;
    }

    public Answer delete(Answer answer) {
        answer.setDeleted(true);

        return answer;
    }

    public Answer findById(long id) {

        return answerRepository.findById(id).orElseThrow(
                () -> new AnswerNotFoundException(id)
        );
    }


}
