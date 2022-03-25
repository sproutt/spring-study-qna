package codesquad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import codesquad.domain.Question;
import codesquad.domain.User;

@Controller
public class QnaController {

	private List<Question> questions = new ArrayList<>();
	private Long index = 0L;

	@GetMapping("/qna")
	public String getForm(){
		return "qna/form";
	}

	@PostMapping("/questions")
	public String ask(Question question){
		question.setIndex(++index);
		questions.add(question);
		return "redirect:/";
	}

	@GetMapping("/")
	public String list(Model model){
		model.addAttribute("questions", questions);
		return "index";
	}

	@GetMapping("/questions/{index}")
	public String show(@PathVariable Long index, Model model){
		for (Question question : questions) {
			if(question.getIndex() == index){
				model.addAttribute("questions", questions);
				break;
			}
		}
		return "qna/show";
	}
}
