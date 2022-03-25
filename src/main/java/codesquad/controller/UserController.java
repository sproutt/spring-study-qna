package codesquad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user")
	public String getForm(){
		return "user/form";
	}

	@PostMapping("/users")
	public String join(User user){
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String list(Model model){
		model.addAttribute("users", users);
		return "list";
	}

	@GetMapping("/users/{userName}")
	public String profile(@PathVariable String userName, Model model){
		for (User user : users) {
			if(user.getUserId().equals(userName)){
				model.addAttribute("user", user);
			}
		}
		return "profile";
	}

	@GetMapping("/user/{id}/form")
	public String updateForm(@PathVariable Long id, Model model){
		for (User user : users){
			if(user.isSameId(id)){
				model.addAttribute("user", user);
				break;
			}
		}
		return "user/updateForm";
	}

	@PostMapping("/users/{id}/update")
	public String update(@PathVariable Long id, User user, Model model){
		for (User savedUser : users){
			if(savedUser.isSameId(id) && savedUser.isSamePassword(user)){
				savedUser.update(user);
				break;
			}
		}
		return "redirect:/users";
	}
}
