package codesquad.utils;

import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;

import java.util.Optional;

public class RepositoryUtil {

    public static Optional<User> findUser(String userId, UserRepository userRepository){
        return userRepository.findByUserId(userId);
    }

    public static Optional<User> findUser(Long id, UserRepository userRepository){
        return userRepository.findById(id);
    }

    public static Optional<Question> findQuestion(Long id, QuestionRepository questionRepository){
        return questionRepository.findById(id);
    }

}
