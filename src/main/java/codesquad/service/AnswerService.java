package codesquad.service;

import codesquad.exception.NullUserException;
import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    private Question findQuestion(Long id){
        return questionRepository.findById(id).orElseThrow(() -> new NullUserException(id));
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new NullUserException(id));
    }

    public void delete(Long id, User user) {
        Answer answer = findById(id);
        if (!answer.isWriter(user)) {
            throw new WrongUserException(user.getId());
        }
        answerRepository.delete(answer);
    }

    public void save(User writer, Answer answer, Long id) {
        answer.setWriter(writer);
        answer.setQuestion(findQuestion(id));
        answer.setId(null);
        answerRepository.save(answer);

    }

}