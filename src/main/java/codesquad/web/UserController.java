package codesquad.web;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.util.HttpSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/form")
    public String userForm() {
        return "user/form";
    }

    @GetMapping("/users/login/form")
    public String userLoginForm() {
        return "user/login";
    }

    @PostMapping("/users")
    public String create(User user) {
        log.info("user = {}", user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public ModelAndView profile(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("user/profile");
        User savedUser = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        mav.addObject("user", savedUser);
        return mav;
    }

    @GetMapping("/users/{id}/form")
    public String userInfoUpdateForm(@PathVariable Long id, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

            if (!sessionedUser.equalsId(id)) {
                httpSession.removeAttribute(HttpSessionUtil.SESSION_KEY);
                throw new IllegalStateException("다른 사람의 개인정보를 수정할 수 없습니다. 다시 로그인 해주세요.");
            }

            model.addAttribute("user", sessionedUser);

        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        }

        return "user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable Long id, User updatedUser, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            if (!HttpSessionUtil.isLoginUser(httpSession)) {
                throw new IllegalStateException("세션이 만료되었습니다. 다시 로그인 해주세요.");
            }

            User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);

            if (!sessionedUser.equalsId(id)) {
                throw new IllegalStateException("아이디가 일치하지 않습니다.");
            }

            if (!sessionedUser.equalsPassword(updatedUser.getPassword())) {
                throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
            }

            sessionedUser.update(updatedUser);

            userRepository.save(sessionedUser);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/users/login/form";
        }

        return "redirect:/users";
    }


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        try {
            User savedUser = userRepository.findByUserId(userId);

            if (savedUser == null) {
                throw new IllegalStateException("아이디와 패스워드를 다시 확인해주세요.");
            }

            if (!savedUser.equalsPassword(password)) {
                throw new IllegalStateException("패스워드가 일치하지 않습니다.");
            }

            httpSession.setAttribute("sessionedUser", savedUser);
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return ":redirect/users/login/form";
        }
        return "redirect:/";
    }
}