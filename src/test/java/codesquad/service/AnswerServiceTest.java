package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.util.HttpSessionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceTest {

    private MockHttpSession session;
    private Question question;

    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUpAnswers() {
        session = new MockHttpSession();
        session.setAttribute("sessionedUser", userService.findUser(Integer.toUnsignedLong(1)));
        question = new Question().builder()
                .title("tmpTilte")
                .contents("asdadasdasdasdasdas")
                .user(HttpSessionUtils.getUserFromSession(session))
                .build();
        questionRepository.save(question);
    }

    @Test
    public void create() {
        List<Answer> beforeAnswerList = (List<Answer>) answerRepository.findAll();
        answerService.create(Integer.toUnsignedLong(1), "replyreplyrepley", session);
        List<Answer> afterAnswerList = (List<Answer>) answerRepository.findAll();
        assertEquals(afterAnswerList.size() - beforeAnswerList.size(), 1);
    }

    @Test
    public void findAnswers() {
        answerService.create(Integer.toUnsignedLong(1), "replyreplyrepley", session);
        assertNotNull(answerService.findAnswers(Integer.toUnsignedLong(1)));
    }

    @Test
    public void delete() {
        answerService.create(Integer.toUnsignedLong(1), "replyreplyrepley", session);
        List<Answer> beforeAnswerList = (List<Answer>) answerRepository.findAll();
        answerService.delete(Integer.toUnsignedLong(1), session);
        List<Answer> afterAnswerList = (List<Answer>) answerRepository.findAll();
        assertEquals(beforeAnswerList.size() - afterAnswerList.size(), 1);
    }
}