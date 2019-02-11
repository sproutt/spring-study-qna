package codesquad.service;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(NoSuchElementException::new);
    }

    public void update(User user, Long id) {
        User userToUpdate = getUserById(id);
        userToUpdate.update(user);
        userRepository.save(userToUpdate);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

}
