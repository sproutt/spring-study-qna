package codesquad;

import codesquad.repository.QuestionRepositoryImpl;
import codesquad.repository.UserRepositoryImpl;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import codesquad.service.QuestionService;
import codesquad.service.QuestionServiceImpl;
import codesquad.service.UserService;
import codesquad.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public QuestionService questionService() {
        return new QuestionServiceImpl(questionRepository());
    }

    @Bean
    public QuestionRepository questionRepository() {
        return new QuestionRepositoryImpl();
    }
}
