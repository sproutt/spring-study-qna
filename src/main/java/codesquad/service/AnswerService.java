package codesquad.service;

import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    public Iterable<Answer> findByQuestionId(Long id){
        Iterable<Answer> answers = answerRepository.findAll();
        List<Answer> returnAnswer = new ArrayList<Answer>();
        for(Answer answer : answers){
            if(answer.getQuestion().getId().equals(id)){
                returnAnswer.add(answer);
            }
        }
        return returnAnswer;
    }

    public void delete(Long id, User user){
        Answer answer = answerRepository.findById(id).get();
        if(!answer.getWriter().getId().equals(user.getId())) {
           throw new WrongUserException(user.getId());
        }
        answerRepository.delete(answer);
    }

    public void save(Answer answer){
        answerRepository.save(answer);
    }

}
