package codesquad.controller;

import codesquad.AppConfig;
import codesquad.dto.user.UserDto;
import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;
import codesquad.mapper.UserMapper;
import codesquad.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    UserService userService = applicationContext.getBean("userService", UserService.class);
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/join")
    public String getRegisterForm() {
        return "user/form";
    }

    @PostMapping()
    public String registerUser(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userService.registerUser(userEntity);

        return "redirect:/users";
    }

    @GetMapping()
    public String getUserList(Model model) {
        List<UserDto> users = userService.findUsers().stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class)).collect(Collectors.toList());
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

    @GetMapping("/{userId}/form")
    public String getProfileEditForm() {
        return "user/update_form";
    }

    @PutMapping("/{userId}/update")
    public String updateUserProfile(@PathVariable("userId") String userId, UserUpdateRequestDto requestDto) {
        userService.updateUser(userId, UserMapper.dtoToEntity(requestDto));
        return "redirect:/users";
    }
}
