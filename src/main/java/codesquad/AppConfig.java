package codesquad;

import codesquad.repository.ArrayUserRepository;
import codesquad.repository.UserRepository;
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
        return new ArrayUserRepository();
    }
}
