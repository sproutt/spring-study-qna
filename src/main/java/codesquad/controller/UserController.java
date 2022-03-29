package codesquad.controller;

import codesquad.domain.User;
import codesquad.exception.NoSuchUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private final List<User> users = new ArrayList<>();
    private Long id = 1L;

    @PostMapping("/users")
    public String create(@ModelAttribute User user) {
        user.createId(id++);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUserListForm(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showUserProfileForm(@PathVariable String userId, Model model) {
        User user = users.stream()
                         .filter(u -> u.isUserIdEqual(userId))
                         .findAny()
                         .orElseThrow(NoSuchUserException::new);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String showEditIndividualInfoForm(@PathVariable Long id) {
        return "user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String editIndividualInfo(@PathVariable Long id, User user, Model model) {
        User editedUser = users.stream()
                               .filter(u -> u.isIdEqual(id))
                               .findAny()
                               .orElseThrow(NoSuchUserException::new);

        editedUser.editInfo(user);
        return "redirect:/users";
    }
}
