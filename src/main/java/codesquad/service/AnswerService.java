package codesquad.service;

import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionService questionService;

    public void delete(Long id, User user){
        Answer answer = answerRepository.findById(id).get();
        if(!answer.isWriter(user)) {
           throw new WrongUserException(user.getId());
        }
        answerRepository.delete(answer);
    }

    public void save(User writer,Answer answer, Long id) {
        answer.setWriter(writer);
        answer.setQuestion(questionService.findById(id));
        answer.setId(null);
        answerRepository.save(answer);

    }

}
