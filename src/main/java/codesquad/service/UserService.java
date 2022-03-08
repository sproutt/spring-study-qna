package codesquad.service;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.stereotype.Service;

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
                .ifPresent(m -> { throw new IllegalArgumentException("이미 사용중인 사용자 ID 입니다");
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(m -> { throw new IllegalArgumentException("이미 사용중인 이메일 주소 입니다.");
                });
    }
}
