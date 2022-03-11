package codesquad.service;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private static final String ALREADY_EXIST_USER_ID_ERROR = "이미 사용중인 사용자 ID 입니다";
    private static final String ALREADY_EXIST_EMAIL_ERROR = "이미 사용중인 사용자 이메일 주소 입니다";
    private static final String NOT_MATCH_EXIST_USER_ID_ERROR = "해당 사용자 아이디와 일치하는 사용자가 없습니다.";

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
                    throw new IllegalArgumentException(ALREADY_EXIST_USER_ID_ERROR);
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalArgumentException(ALREADY_EXIST_EMAIL_ERROR);
                });
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(NOT_MATCH_EXIST_USER_ID_ERROR));
    }
}
