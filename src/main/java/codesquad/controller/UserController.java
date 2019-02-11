package codesquad.controller;

import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import codesquad.utils.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String goToForm() {
        return "/users/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String returnList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String goToProfile(Model model, @PathVariable Long id) {
        Optional<User> user = RepositoryUtil.findUser(id, userRepository);
        if (!user.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", user.get());
        return "/users/profile";
    }


    @GetMapping("/loginForm")
    public String goTOLoginForm() {
        return "/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> user = RepositoryUtil.findUser(userId, userRepository);
        if (!user.isPresent()) {
            return "/users/login_failed";
        }
        if (!user.get().getPassword().equals(password)) {
            return "/users/login_failed";
        }
        session.setAttribute("user", user.get());
        return "redirect:/";
    }


    @GetMapping("/updateForm")
    public String gotoUpdateForm() {
        return "/users/updateForm";
    }

    @PostMapping("/update/{id}")
    public String updateUser(User newUser, HttpServletResponse response, @PathVariable Long id) throws Exception {
        Optional<User> user = RepositoryUtil.findUser(id, userRepository);
        if (!user.isPresent()) {
            return "redirect:/";
        }
        if (!user.get().getPassword().equals(newUser.getPassword())) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인 정보를 확인해주세요.'); history.go(-1);</script>");
            out.flush();
            return "/users/updateForm";
        }
        user.get().update(newUser);
        userRepository.save(user.get());
        return "redirect:/users/logout";
    }

}
