package codesquad.service;

import codesquad.domain.User;
import codesquad.exception.ExistEmailException;
import codesquad.exception.ExistUserIdException;
import codesquad.exception.NoSuchUserException;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(m -> {
                    throw new ExistUserIdException();
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new ExistEmailException();
                });
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(NoSuchUserException::new);
    }
}
