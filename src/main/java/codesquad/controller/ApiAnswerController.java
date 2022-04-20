package codesquad.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;

@RestController
@RequestMapping("/api/questions/{index}/answers")
public class ApiAnswerController {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public Answer create(@PathVariable Long index, @RequestBody String contents, HttpSession session){
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			return null;
		}
		Question question = questionRepository.findById(index).orElseThrow(NoSuchElementException::new);
		Answer answer = new Answer(sessionedUser, contents, question);
		System.out.println(answerRepository.save(answer));
		return answerRepository.save(answer);
	}

	@DeleteMapping("/{index}")
	public Map<String, Long> delete(@PathVariable Long index, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			return null;
		}
		Answer answer = answerRepository.findById(index).orElseThrow(NoSuchElementException::new);
		answerRepository.delete(answer);

		Map<String, Long> tmp = new HashMap<>();
		tmp.put("index", answer.getIndex());
		return tmp;
	}
}
