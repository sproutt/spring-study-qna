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

    public Answer save(HttpSession session, String content, long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(()-> new QuestionNotFoundException(questionId));
        Answer answer = new Answer(question, SessionUtil.getSessionUser(session), content);
        answerRepository.save(answer);
        return answer;
    }

    public void delete(HttpSession session, long id) {
        SessionUtil.checkAuth(session, getWriter(id));
        Answer answer = this.findById(id);
        answer.setDeleted(true);
    }

    public Answer findById(long id) {

        return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
    }

    public User getWriter(long id){
        return answerRepository.findWriterById(id).orElseThrow(() -> new AnswerNotFoundException(id));
    }

}
