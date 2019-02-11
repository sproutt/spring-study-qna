package codesquad.service;

import codesquad.model.User;
import codesquad.repository.UserRepository;
import codesquad.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

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
        if(!user.isSamePassword(userToUpdate))
            throw new CustomException("비밀번호가 다릅니다");
        userToUpdate.update(user);
        userRepository.save(userToUpdate);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
