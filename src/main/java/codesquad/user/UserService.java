package codesquad.user;

import codesquad.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void create(SingUpUserDto singUpUserDto) {
        log.info("singUpUserDto = {}", singUpUserDto);
        userRepository.save(new User(singUpUserDto));
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean update(SingUpUserDto changedUser, long userId, HttpSession session) {
        log.info("changedUser = {}", changedUser);
        log.info("userId = {}", userId);

        User user = SessionUtil.getUserBySession(session);

        if (user == null || !user.equalsId(userId)) {
            return false;
        }

        user.update(changedUser);
        userRepository.save(user);

        return true;
    }

    public boolean login(LoginUserDto loginUserDto, HttpSession session) {
        User savedUser = userRepository.findByUserId(loginUserDto.getUserId())
                                       .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        if (savedUser.equalsPassword(loginUserDto)) {
            SessionUtil.setSession(session, savedUser);
            return true;
        }

        return false;
    }

    public User findUserById(long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(NoSuchElementException::new);
    }
}
