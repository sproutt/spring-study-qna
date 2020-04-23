package codesquad.service;

import codesquad.domain.Answer;
import codesquad.repository.AnswerRepository;
import codesquad.util.HttpSessionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public void create(Long questionId, String answer, HttpSession session) {
        answerRepository.save(new Answer()
                .builder()
                .question(questionService.findQuestion(questionId))
                .answer(answer)
                .writer(HttpSessionUtils.getUserFromSession(session))
                .build());
    }

    public void delete(Long id, HttpSession session) {
        if (!answerRepository.findById(id).orElseThrow(NullPointerException::new).isSameWriter(HttpSessionUtils.getUserFromSession(session))) {
            throw new IllegalStateException("You can't delete other's answer");
        }
        answerRepository.deleteById(id);
    }

    public List<Answer> findAnswers(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
}
