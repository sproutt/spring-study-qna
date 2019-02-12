package codesquad.net.slipp.web.service;

import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean checkIdPassword(User inputUser) {
        String inputUserId = inputUser.getUserId();
        User modelUser = userRepository.findByUserId(inputUserId).orElseThrow(
                () -> new UserNotFoundException(inputUserId)
        );

        return modelUser.getPassword().equals(inputUser.getPassword());
    }

    public User findByUserId(String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        return user;
    }

    public User findById(long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );

        return user;
    }

    public void update(User modelUser, User updateUser){
        modelUser.setName(updateUser.getName());
        modelUser.setEmail(updateUser.getEmail());
        userRepository.save(modelUser);
    }





}
