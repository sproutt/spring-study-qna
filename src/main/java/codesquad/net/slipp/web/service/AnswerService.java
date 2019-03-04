package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.*;
import codesquad.net.slipp.web.exception.AnswerNotFoundException;
import codesquad.net.slipp.web.exception.QuestionNotFoundException;
import codesquad.net.slipp.web.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer save(HttpSession session, String contents, long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException(questionId));
        Answer answer = new Answer(question, SessionUtil.getSessionUser(session), contents);

        return answerRepository.save(answer);
    }

    public Answer delete(HttpSession session, long id) {
        SessionUtil.checkAuth(session, this.getWriter(id));
        Answer answer = this.findById(id);
        answer.setDeleted(true);

        return answerRepository.save(answer);
    }

    public Answer findById(long id) {
        return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
    }

    public User getWriter(long id) {
        return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id)).getWriter();
    }

}
