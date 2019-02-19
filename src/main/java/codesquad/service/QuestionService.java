package codesquad.service;

import codesquad.exception.CannotDeleteQuestionException;
import codesquad.exception.QuestionNotExistException;
import codesquad.exception.UserNotEqualException;
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
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotExistException::new);
    }

    public void update(Question question, User writer) {
        question.update(question, writer);
        questionRepository.save(question);
    }

    public void delete(User sessionedUser, Long id) {
        Question questionToDelete = getQuestionById(id);
        boolean isDeleted = false;
        if (questionToDelete.getAnswers().size() == 0) {
            isDeleted = true;
        }
        if (questionToDelete.isEqualWriter(sessionedUser)) {
            isDeleted = true;
        }
        if (questionToDelete.getAnswers().stream()
                .allMatch(answer -> answer.getWriter()
                        .isSameUser(questionToDelete.getWriter()))) {
            isDeleted = true;
        }
        if (isDeleted) {
            questionToDelete.getAnswers().stream()
                    .forEach(answer -> answer.setDeleted(true));
            questionToDelete.setDeleted(true);
            questionRepository.save(questionToDelete);
        } else {
            throw new CannotDeleteQuestionException();
        }
    }

    public Question getUpdatedQuestion(User sessionedUser, Long id) {
        Question questionToUpdate = getQuestionById(id);
        if (!questionToUpdate.isEqualWriter(sessionedUser)) {
            throw new UserNotEqualException();
        }
        return questionToUpdate;
    }

    public void addAnswer(Long questionId, HttpSession session, String contents) {
        Question question = getQuestionById(questionId);
        User writer = SessionChecker.loggedinUser(session);
        question.addAnswer(new Answer(question, writer, contents));
        questionRepository.save(question);
    }
}
