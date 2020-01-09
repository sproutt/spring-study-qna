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

    public void create(User user) {
        userRepository.save(user);
    }

    public String findUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    public String findUser(Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return "user/profile";
    }

    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
        if (user.isSamePassword(password)) {
            session.setAttribute("sessionedUser", user);
            return "redirect:/users";
        } else {
            return "user/login_failed";
        }
    }

    public void logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        session.invalidate();
    }

    public String update(Long id, Model model, HttpSession session) {
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
