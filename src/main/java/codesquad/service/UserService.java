package codesquad.service;

import codesquad.exception.UserNotEqualException;
import codesquad.exception.UserNotExistException;
import codesquad.model.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotExistException::new);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotExistException::new);
    }

    public void update(User user, Long id) {
        User userToUpdate = getUserById(id);
        if (!user.isSamePassword(userToUpdate))
            throw new UserNotEqualException();
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
