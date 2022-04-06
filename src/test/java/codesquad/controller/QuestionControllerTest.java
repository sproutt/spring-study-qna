package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.domain.WriteQuestionDto;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("질문을 등록했을때 질문이 잘 조회되는지 테스트")
    void 질문_등록() {
        // given
        User user1 = userRepository.findById(1L)
                                  .get();

        LocalDateTime localDateTime = LocalDateTime.now();
        Question question = new Question(user1, "title1", "content1", localDateTime);

        // when
        questionRepository.save(question);

        // then
        Question result = questionRepository.findByWriter(user1)
                                            .get();
        assertThat(question).isEqualTo(result);
    }

    @Test
    @DisplayName("질문이 여러개가 등록되었을때 질문 목록 크기가 일치하는지 테스트")
    void 질문목록_조회() {
        // given
        User user1 = userRepository.findById(1L)
                                   .get();
        User user2 = userRepository.findById(2L)
                                   .get();

        LocalDateTime localDateTime = LocalDateTime.now();
        Question question1 = new Question(user1, "title1", "content1", localDateTime);
        Question question2 = new Question(user2, "title2", "content2", localDateTime);

        // when
        questionRepository.save(question1);
        questionRepository.save(question2);

        // then
        List<Question> result = questionRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("등록된 질문을 수정하였을때 내용이 잘 수정되는지 확인")
    void 질문_수정() {
        // given
        User user1 = userRepository.findById(1L)
                                   .get();

        LocalDateTime localDateTime = LocalDateTime.now();
        Question question1 = new Question(user1, "title1", "content1", localDateTime);
        WriteQuestionDto writeQuestionDto = new WriteQuestionDto("수정된 제목", "수정된 내용");

        // when
        questionRepository.save(question1);
        Question question = questionRepository.findByWriter(user1)
                                            .get();
        question.write(user1, writeQuestionDto, localDateTime);
        questionRepository.save(question);

        // then
        Question result = questionRepository.findByWriter(user1)
                                               .get();
        assertThat(result.getTitle()).isEqualTo(question.getTitle());
    }

    @Test
    @DisplayName("질문을 삭제했을때 정상적으로 삭제가 되는지 테스트")
    void 질문_삭제() {
        // given
        User user1 = userRepository.findById(2L)
                                  .get();

        LocalDateTime localDateTime = LocalDateTime.now();
        Question question = new Question(user1, "title1", "content1", localDateTime);

        // when
        questionRepository.save(question);
        questionRepository.delete(questionRepository.findByWriter(user1)
                                                    .get());
        // then
        List<Question> result = questionRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}