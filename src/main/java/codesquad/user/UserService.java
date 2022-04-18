package codesquad.user;

import codesquad.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void create(User user) {
        log.info("user = {}", user);
        userRepository.save(user);
    }

    public void list(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }

    public void show(long userId, ModelAndView modelAndView) {
        modelAndView.addObject("user", findUserById(userId));
    }

    public void setUpdateForm(Model model, long userId) {
        model.addAttribute("user", findUserById(userId));
    }

    @Transactional
    public boolean update(User changedUser, long userId, HttpSession session) {
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

    public void login(UserDto userDto, HttpSession session) {
        User savedUser = userRepository.findByUserId(userDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        if (savedUser.equalsPassword(userDto)) {
            SessionUtil.setSession(session, savedUser);
        }
    }

    private User findUserById(long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(NoSuchElementException::new);
    }
}
