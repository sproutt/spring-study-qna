package codesquad.service;

import codesquad.exception.NullQuestionException;
import codesquad.exception.WrongUserException;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.utils.QuestionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    QuestionUtils questionUtils = new QuestionUtils();

    public Question findById(Long id){
        return questionRepository.findById(id).orElseThrow(() -> new NullQuestionException(id));
    }

    public Iterable<Question> findAll(){
        return questionRepository.findAll();
    }

    public void update(Long id, Question newQuestion){
        Question question = findById(id);
        question.update(newQuestion);
        questionRepository.save(question);
    }

    public void delete(Long id, User user){
        Question question = findById(id);
        if(!questionUtils.isWriter(question,user)) {
            throw new WrongUserException(id);
        }
        questionRepository.deleteById(id);
    }

    public void save(Question question){
        questionRepository.save(question);
    }

}
