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

    @Test
    void 질문_등록_테스트() {
        // given
        QuestionEntity questionEntity = QuestionEntity.builder()
                .writer("작성자")
                .title("제목")
                .contents("제목")
                .build();

        // when
        questionService.postQuestion(questionEntity);
        List<QuestionEntity> questionEntityList = questionService.findQuestions();

        // then
        Assertions.assertEquals(questionEntityList.get(0).getWriter(), questionEntity.getWriter());
    }

    @Test
    void contextLoads() {
        System.out.println(org.springframework.core.SpringVersion.getVersion());
    }
}