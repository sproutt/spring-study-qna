package codesquad.utils;

import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class OptionalProcessor {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    public Question optionalToQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public User getUserByUserId(String userId) {
        System.out.println("test");
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("존재하는 회원 정보가 일치하지 않습니다."));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("존재하지 않는 회원입니다"));
    }
}