package codesquad.controller;

import codesquad.domain.Question;
import codesquad.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuestionControllerTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("질문을 등록했을때 질문이 잘 조회되는지 테스트")
    void 질문_등록() {
        // given
        LocalDateTime localDateTime = LocalDateTime.now();
        Question question = new Question("writer1", "title1", "content1", localDateTime);

        // when
        questionRepository.save(question);

        // then
        Question result = questionRepository.findByWriter("writer1")
                                            .get();
        assertThat(question).isEqualTo(result);
    }

    @Test
    @DisplayName("질문이 여러개가 등록되었을때 질문 목록 크기가 일치하는지 테스트")
    void 질문목록_조회() {
        // given
        LocalDateTime localDateTime = LocalDateTime.now();
        Question question = new Question("writer1", "title1", "content1", localDateTime);
        Question question2 = new Question("writer2", "title2", "content2", localDateTime);
        Question question3 = new Question("writer3", "title3", "content3", localDateTime);
        Question question4 = new Question("writer4", "title4", "content4", localDateTime);

        // when
        questionRepository.save(question);
        questionRepository.save(question2);
        questionRepository.save(question3);
        questionRepository.save(question4);

        // then
        List<Question> result = questionRepository.findAll();
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("질문을 삭제했을때 정상적으로 삭제가 되는지 테스트")
    void 질문_삭제() {
        // given
        LocalDateTime localDateTime = LocalDateTime.now();
        Question question = new Question("writer1", "title1", "content1", localDateTime);

        // when
        questionRepository.save(question);
        questionRepository.delete(questionRepository.findByWriter("writer1")
                                                    .get());
        // then
        List<Question> result = questionRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}