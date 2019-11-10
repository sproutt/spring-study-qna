package codesquad.service;

import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return user;
    }

    public String userLogin(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        User user = maybeUser.orElseThrow(NullPointerException::new);
        if (user.isSamePassword(password)) {
            session.setAttribute("sessionedUser", user);
            return "redirect:/users";
        } else {
            return "user/login_failed";
        }
    }

    public void userLogout(HttpSession session) {
        session.removeAttribute("seesionedUser");
        session.invalidate();
    }

    public String userUpdate(Long id, Model model, HttpSession session) {
        userRepository.findById(id).orElseThrow(NullPointerException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User user = (User) value;
            if (id.equals(user.getId())) {
                model.addAttribute("user", user);
                return "user/updateForm";
            } else {
                model.addAttribute("userMissMatch", userRepository.findAll());
                return "user/list";
            }
        } else {
            return "user/login";
        }
    }

    public String userEdit(Long id, User user, HttpSession session) {
        userRepository.findById(id).orElseThrow(NullPointerException::new);
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            User originUser = (User) value;
            if (originUser.isSamePassword(user.getPassword())) {
                originUser.editProfile(user);
                userRepository.save(originUser);
                return "redirect:/users";
            } else {
                return "user/updateForm";
            }
        } else {
            return "user/login";
        }
    }
}
