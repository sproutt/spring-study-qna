package codesquad.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;

@Controller
public class QnaController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/qna")
	public String getForm() {
		return "qna/form";
	}

	@GetMapping("/")
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}

	@PostMapping("/questions")
	public String ask(Question question, HttpSession session) {
		User sessionedUser = (User)session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "redirect:/login";
		}
		question.setWriter(sessionedUser);
		questionRepository.save(question);
		return "redirect:/";
	}

	@GetMapping("/questions/{index}")
	public ModelAndView show(@PathVariable Long index, HttpSession session) {
		ModelAndView mav = new ModelAndView("qna/show");
		Question question = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
		mav.addObject("question", question);
		return mav;
	}

	@GetMapping("/questions/{index}/form")
	public String updateForm(@PathVariable Long index, Model model, HttpSession session) {
		User sessionedUser = (User)session.getAttribute("sessionedUser");
		Question savedQuestion = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);

		if (!sessionedUser.isSameId(savedQuestion.getWriter().getUserId())) {
			return "failed";
		}

		model.addAttribute("question", savedQuestion);
		return "qna/updateForm";
	}

	@PutMapping("/questions/{index}")
	public String update(@PathVariable Long index, Question updatedQuestion, HttpSession session) {
		User sessionedUser = (User)session.getAttribute("sessionedUser");
		Question question = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
		if (question.isSameIndex(index)) {
			question.update(updatedQuestion);
			questionRepository.save(question);
		}
		return "redirect:/";

	}

	@DeleteMapping("/questions/{index}")
	public String delete(@PathVariable Long index, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		Question question = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
		if(!question.isSameWriter(sessionedUser)){
			return "redircet:/questions/{index}";
		}

		if(!question.isSameWriter(sessionedUser)) {
			return "redirect:/login";
		}
		questionRepository.delete(question);
		return "redirect:/";
	}
}
