package codesquad.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;

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
	public String ask(Question question) {
		questionRepository.save(question);
		return "redirect:/";
	}

	@GetMapping("/questions/{index}")
	public String show(@PathVariable Long index, Model model) {
		Optional<Question> question = questionRepository.findById(index);
		model.addAttribute("question", question.get());
		return "qna/show";
	}

	@GetMapping("/questions/{index}/form")
	public String updateForm(@PathVariable Long index, Model model) {
		Optional<Question> savedQuestion = questionRepository.findById(index);
		if(savedQuestion.get().isSameIndex(index)) {
			model.addAttribute("question", savedQuestion.get());
		}
		return "qna/updateForm";
	}

	@PostMapping("/questions/{index}")
	public String update(@PathVariable Long index, Question updatedQuestion) {
		Optional<Question> question = questionRepository.findById(index);
		if(updatedQuestion.isSameIndex(question.get().getIndex())) {
			question.get().update(updatedQuestion);
			questionRepository.save(question.get());
		}
		return "redirect:/";
	}

	@DeleteMapping("/questions/{index}")
	public String delete(@PathVariable Long index) {
		Optional<Question> question = questionRepository.findById(index);
		questionRepository.delete(question.get());
		return "redirect:/";
	}
}
