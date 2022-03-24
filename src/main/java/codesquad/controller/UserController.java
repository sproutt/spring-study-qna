package codesquad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import codesquad.domain.User;

@Controller
public class UserController {

	private List<User> users = new ArrayList<>();
	private Long sequence = 0L;

	@GetMapping("/user")
	public String getForm(){
		return "user/form";
	}

	@PostMapping("/users")
	public String join(User user){
		user.setId(++sequence);
		users.add(user);
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
			if(user.getId() == user.getId()){
				model.addAttribute("user", user);
				break;
			}
		}
		return "user/updateForm";
	}

	@PostMapping("/users/{id}/update")
	public String update(@PathVariable Long id, User user, Model model){
		for (User savedUser : users){
			if(savedUser.getId() == id && savedUser.validatePassword(user)){
				savedUser.update(user);
				break;
			}
		}
		return "redirect:/users";
	}
}
