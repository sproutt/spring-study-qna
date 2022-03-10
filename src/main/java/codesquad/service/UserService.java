package codesquad.service;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long join(User user) {
        return userRepository.save(user);
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User findOne(Long id) {
        return userRepository.findById(id);
    }
}
