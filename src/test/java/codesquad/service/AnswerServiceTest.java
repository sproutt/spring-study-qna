package codesquad.service;

import codesquad.domain.Answer;
import codesquad.repository.AnswerRepository;
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

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    private MockHttpSession session;

    private AnswerService answerService;

    @Before
    public void setUpAnswers() {
        answerService = new AnswerServiceImpl(answerRepository, questionService);
        session = new MockHttpSession();
        session.setAttribute("sessionedUser", userService.findUser(Integer.toUnsignedLong(1)));
        questionService.create("tmpTitle", "asdasdasd", session);
    }

    @Test
    public void create() {
        List<Answer> beforeAnswerList = (List<Answer>) answerService.findAnswers(Integer.toUnsignedLong(1));
        answerService.create(Integer.toUnsignedLong(1), "replyreplyrepley", session);
        List<Answer> afterAnswerList = (List<Answer>) answerService.findAnswers(Integer.toUnsignedLong(1));
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
        List<Answer> beforeAnswerList = (List<Answer>) answerService.findAnswers(Integer.toUnsignedLong(1));
        answerService.delete(Integer.toUnsignedLong(1), session);
        List<Answer> afterAnswerList = (List<Answer>) answerService.findAnswers(Integer.toUnsignedLong(1));
        assertEquals(beforeAnswerList.size() - afterAnswerList.size(), 1);
    }
}