package codesquad.web;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.AnswerRepository;
import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import codesquad.domain.user.User;
import codesquad.util.HttpSessionUtil;
import codesquad.web.dto.AnswerSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/questions/{index}/answers")
public class ApiAnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long index, @RequestBody AnswerSaveRequestDto answerSaveRequestDto, HttpSession httpSession) {
        if(!HttpSessionUtil.isLoginUser(httpSession)) {
            return null;
        }

        User sessionedUser = HttpSessionUtil.getUserFrom(httpSession);
        Question savedQuestion = questionRepository.findById(index).orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        Answer answer = new Answer(sessionedUser, savedQuestion, answerSaveRequestDto.getContents());
        savedQuestion.addCountOfAnswer();

        return answerRepository.save(answer);
    }
}
