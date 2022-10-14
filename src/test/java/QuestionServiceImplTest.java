import codesquad.entity.QuestionEntity;
import codesquad.repository.QuestionRepositoryImpl;
import codesquad.service.QuestionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {
    @Mock
    private QuestionRepositoryImpl questionRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void 질문_등록_테스트() {
        QuestionEntity questionEntity = questionList().get(0);

        doThrow(RuntimeException.class).when(questionRepository).post(questionEntity);
        Assertions.assertThrows(RuntimeException.class, () -> questionService.postQuestion(questionEntity));
    }

    @Test
    void 모든_질문_불러오기_테스트() {
        // given
        doReturn(questionList()).when(questionRepository)
                .findAll();

        // when
        List<QuestionEntity> questionList = questionService.findQuestions();

        // then
        Assertions.assertEquals(questionList.size(), 1);
    }

    List<QuestionEntity> questionList() {
        List<QuestionEntity> questionList = new ArrayList<>();
        questionList.add(new QuestionEntity("write", "title", "contents"));
        return questionList;
    }
}