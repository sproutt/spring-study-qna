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

}
