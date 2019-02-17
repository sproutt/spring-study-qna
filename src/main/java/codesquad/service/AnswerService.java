package codesquad.service;

import codesquad.exception.AnswerNotExistException;
import codesquad.exception.UserNotEqualException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.result.AnswerResult;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionService questionService;

    public Answer getAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow(AnswerNotExistException::new);
    }

    public Answer save(Answer answer) {
        answerRepository.save(answer);
        return answer;
    }

    public void create(HttpSession session, Long questionId, String contents) {
        User sessionUser = SessionChecker.loggedinUser(session);
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer(question, sessionUser, contents);
        answerRepository.save(answer);
    }

    public boolean delete(HttpSession session, Long questionId, Long id) {
        User sessionUser = SessionChecker.loggedinUser(session);
        Answer answer = getAnswerById(id);
        if (!sessionUser.isSameUser(answer.getWriter())) {
            throw new UserNotEqualException();
        }
        answer.setDeleted(true);
        answerRepository.save(answer);
        return getAnswerById(id).getDeleted();
    }

}
