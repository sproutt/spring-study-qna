package codesquad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.QuestionRepository;

@Controller
public class AnswerController {
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;
}
