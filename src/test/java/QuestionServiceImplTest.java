import codesquad.entity.QuestionEntity;
import codesquad.repository.ArrayQuestionRepository;
import codesquad.repository.QuestionRepository;
import codesquad.service.QuestionService;
import codesquad.service.QuestionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository = new ArrayQuestionRepository();

    @InjectMocks
    private QuestionService questionService = new QuestionServiceImpl(questionRepository);

    void setUp() {
        QuestionEntity questionEntity = QuestionEntity.builder()
                .writer("july")
                .title("title")
                .contents("contents")
                .build();

        questionService.postQuestion(questionEntity);
    }

    @Test
    void 질문_등록_테스트() {
        // given
        QuestionEntity questionEntity = QuestionEntity.builder()
                .writer("july")
                .title("title")
                .contents("contents")
                .build();

        // when
        questionService.postQuestion(questionEntity);
        List<QuestionEntity> questionEntityList = questionService.findQuestions();

        // then
        Assertions.assertEquals(questionEntityList.get(0).getWriter(), questionEntity.getWriter());
    }

    @Test
    void 모든_질문_불러오기_테스트() {
        // given
        setUp();

        // when
        List<QuestionEntity> testQuestionEntity = questionService.findQuestions();

        // then
        Assertions.assertEquals(testQuestionEntity.size(), 1);
    }

    @Test
    void 특정_질문_불러오기_테스트() {
        // given
        setUp();

        // when
        QuestionEntity questionEntity = questionService.findQuestion("1");

        // then
        Assertions.assertEquals(questionEntity.getTitle(), "title");
    }
}