package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;
    public boolean checkLogin(User inputUser) {
        String inputUserId = inputUser.getUserId();
        User modelUser = userRepository.findByUserId(inputUserId).orElseThrow(
                () -> new UserNotFoundException(inputUserId)
        );

        return modelUser.getPassword().equals(inputUser.getPassword());
    }
}
