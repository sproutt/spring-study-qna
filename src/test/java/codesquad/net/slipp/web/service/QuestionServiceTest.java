package codesquad.net.slipp.web.service;


import codesquad.net.slipp.web.domain.Question;
import codesquad.net.slipp.web.domain.QuestionRepository;
import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class QuestionServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Test
    public void findByIdTest(){
        Question question = new Question();
        question.setWriter(userService.findByUserId("test"));
        question.setTitle("testTitle");
        question.setContents("testContent");
        questionService.save(question);

        assertThat(questionService.findById(1).getTitle()).isEqualTo("testTitle");
    }

    @Test
    public void updateTest(){
        Question question = new Question();
        question.setWriter(userService.findByUserId("test"));
        question.setTitle("testTitle");
        question.setContents("testContent");
        questionService.save(question);

        Question modelQuestion = questionService.findById(1);
        Question updatedQuestion = new Question();
        updatedQuestion.setTitle("modifiedTitle");
        updatedQuestion.setContents("modifiedContent");

        questionService.update(modelQuestion, updatedQuestion);
        assertThat(questionService.findById(1).getContents()).isEqualTo("modifiedContent");
    }

}