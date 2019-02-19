package codesquad.service;

import codesquad.common.RestResponse;
import codesquad.dto.AnswerDTO;
import codesquad.exception.AnswerNotExistException;
import codesquad.exception.QuestionNotExistException;
import codesquad.exception.UserNotLoginException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.utils.SessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer getAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow(AnswerNotExistException::new);
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(QuestionNotExistException::new);
    }

    public Answer save(Answer answer) {
        answerRepository.save(answer);
        return answer;
    }

    public void create(HttpSession session, Long questionId, String contents) {
        User sessionUser = SessionChecker.loggedinUser(session);
        Answer answer = new Answer(questionId, sessionUser, contents);
        answerRepository.save(answer);
    }

    public void create(HttpSession session, Long questionId, AnswerDTO answerDTO) {
        if (!SessionChecker.isLoggedIn(session)) {
            throw new UserNotLoginException();
        }
        User writer = SessionChecker.loggedinUser(session);
        Question question = getQuestionById(questionId);
        Answer answer = new Answer(question, writer, answerDTO.getContents());
        question.addAnswer(answer);
        answerRepository.save(answer);
    }

    public boolean delete(HttpSession session, Long questionId, Long id) {
        Answer answer = getAnswerById(id);
        SessionChecker.matchUserToAnswer(session, answer);
        answer.setDeleted(true);
        answerRepository.save(answer);
        return getAnswerById(id).isDeleted();
    }

}
