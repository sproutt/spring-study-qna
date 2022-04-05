package codesquad.controller;

import javax.servlet.http.HttpSession;

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
	public String getForm() {
		return "user/form";
	}

	@PostMapping("/users")
	public String join(User user) {
		System.out.println("user : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}

	@GetMapping("/users/{id}")
	public ModelAndView show(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("user/profile");
		mav.addObject("user", userRepository.findById(id).get());
		return mav;
	}

	@GetMapping("/users/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Object sessionedUser = session.getAttribute("sessionedUser");

		if(sessionedUser == null) {
			return "redirect:/login";
		}

		User user = (User) sessionedUser;
		model.addAttribute("user", user);
		return "user/updateForm";
	}

	@PostMapping("/users/{id}/update")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionedUser");

		if(sessionedUser.isSamePassword(updatedUser.getPassword())) {
			sessionedUser.update(updatedUser);
			userRepository.save(sessionedUser);
		}
		return "redirect:/users";
	}

	@GetMapping("/users/login/form")
	public String userLoginForm(){
		return "user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session){
		User savedUser = userRepository.findByUserId(userId);

		if(savedUser == null){
			return "redirect:/login";
		}

		if(!savedUser.isSamePassword(password)){
			return "redirect:/login";
		}

		session.setAttribute("sessionedUser", savedUser);
		return "redirect:/";
	}




}
