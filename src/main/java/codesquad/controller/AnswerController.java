package codesquad.controller;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;

@Controller
public class AnswerController {
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;

	@DeleteMapping("/questions/{index}/answers/{index}")
	public String delete(@PathVariable Long index, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		Answer answer = answerRepository.findById(index).orElseThrow(NoSuchElementException::new);
		if(!answer.isSameWriter(sessionedUser)){
			return "redircet:/questions/{index}/answers";
		}

		if(sessionedUser == null) {
			return "redirect:/login";
		}
		answerRepository.delete(answer);
		return "redirect:/questions/{index}";
	}
}
