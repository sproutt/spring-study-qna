package codesquad.controller;

import codesquad.AppConfig;
import codesquad.dto.UserDto;
import codesquad.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    UserService userService = applicationContext.getBean("userService", UserService.class);

    @GetMapping("/join")
    public String getRegisterForm() {
        return "user/form";
    }

    @PostMapping()
    public String registerUser(UserDto userDto) {
        userService.registerUser(userDto);

        return "redirect:/users";
    }

    @GetMapping()
    public String getUserList(Model model) {
        List<UserDto> users = userService.findUsers().stream().map(UserDto::new).collect(Collectors.toList());
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {

        UserDto user = new UserDto(userService.findUser(userId));

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());

        return "user/profile";
    }
}
