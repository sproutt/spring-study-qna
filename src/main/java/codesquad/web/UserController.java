package codesquad.web;

import codesquad.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }
}
