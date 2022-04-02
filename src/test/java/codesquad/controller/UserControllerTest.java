package codesquad.controller;

import codesquad.domain.EditUserDto;
import codesquad.domain.User;
import codesquad.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("한명의 사용자가 회원가입 했을때 사용자가 잘 조회되는지 테스트")
    void 회원가입() {
        // given
        User user1 = new User("user1", "1", "채상엽", "email1@gmail.com");

        // when
        userRepository.save(user1);

        // then
        User result = userRepository.findByUserId("user1")
                                    .get();

        assertThat(user1).isEqualTo(result);
    }

    @Test
    @DisplayName("여러명의 사용자가 회원가입 했을때 사용자 목록의 크기가 일치하는지 테스트")
    void 회원목록_조회() {
        // given
        User user1 = new User("user1", "1", "채상엽", "email1@gmail.com");
        User user2 = new User("user2", "1", "홍동건", "email2@gmail.com");
        User user3 = new User("user3", "1", "김성혁", "email3@gmail.com");
        User user4 = new User("user3", "1", "김현우", "email4@gmail.com");

        // when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        // then
        List<User> results = userRepository.findAll();
        assertThat(results.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("회원 정보를 수정했을때 수정 내용이 잘 반영되는지 테스트")
    void 회원정보_수정() {
        // given
        User user1 = new User("user1", "1", "채상엽", "email1@gmail.com");
        EditUserDto editUserDto = new EditUserDto("1", "홍동건", "email2@gmail.com");

        // when
        user1.update(editUserDto);

        // then
        assertThat(user1.getName()).isEqualTo("홍동건");
    }

    @Test
    @DisplayName("로그인 했을때 세션에 사용자 정보가 잘 저장 되는지 테스트")
    void 로그인() {
        // given
        User user1 = new User("user1", "1", "채상엽", "email1@gmail.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user1", user1);

        // when
        userRepository.save(user1);
        userController.login("user1", "1", session);

        // then
        assertThat(session.getAttribute("user1")).isEqualTo(user1);
    }
}