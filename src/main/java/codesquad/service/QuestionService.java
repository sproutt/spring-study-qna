package codesquad.service;

import codesquad.exception.NullQuestionException;
import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.utils.QuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    QuestionUtils questionUtils = new QuestionUtils();

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NullQuestionException(id));
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public void update(Long id, Question newQuestion) {
        Question question = findById(id);
        question.update(newQuestion);
        questionRepository.save(question);
    }

    public void delete(Long id, User user) {
        Question question = findById(id);
        isAuthority(question, user);
        questionRepository.deleteById(id);
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public boolean isAuthority(Question question, User user) {
        if (!questionUtils.isWriter(question, user)) {
            throw new WrongUserException(user.getId());
        }
        return true;
    }

    public List<Answer> getAnswer(Long id) {
        Question question = findById(id);
        return question.getAnswers();
    }

    public int getAnswerSize(Long id){
        List<Answer> answers = getAnswer(id);
        return answers.size();
    }

}
