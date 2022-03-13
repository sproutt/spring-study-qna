package codesquad.service;

import codesquad.domain.User;
import codesquad.exception.ExistEmailException;
import codesquad.exception.ExistUserIdException;
import codesquad.exception.NoSuchUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void 사용자_한명_생성() {
        user = User.craeteUser("saint6839", "1234", "채상엽", "saint6839@gmail.com");
    }

    @Test
    @DisplayName("회원가입을한 아이디로 사용자를 조회했을때 가입한 사용자와 일치하는지 테스트")
    void 사용자_회원가입() {
        // given
        // when
        userService.join(user);

        // then
        assertThat(user).isSameAs(userService.findByUserId("saint6839"));
    }

    @Test
    @DisplayName("여러명의 사용자가 가입하고 목록을 조회했을때 List의 크기가 일치하는지 테스트")
    void 사용자_목록_조회() {
        // given
        User user2 = User.craeteUser("holgildong", "4321", "홍길동", "hongildong@gmail.com");

        // when
        userService.join(user);
        userService.join(user2);

        // then
        assertThat(userService.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("동일한 사용자 아이디로 회원가입 했을때 예외 검증")
    void 중복_UserId_사용자_검증() {
        // given
        User user2 = User.craeteUser("saint6839", "4321", "홍길동", "hongildong@gmail.com");

        // when
        userService.join(user);

        // then
        assertThatThrownBy(() -> userService.join(user2))
                .isInstanceOf(ExistUserIdException.class)
                .hasMessage("이미 사용중인 사용자 ID 입니다");
    }

    @Test
    @DisplayName("동일한 이메일로 회원가입 했을때 예외 검증")
    void 중복_Email_사용자_검증() {
        // given
        User user2 = User.craeteUser("gildong", "4321", "홍길동", "saint6839@gmail.com");

        // when
        userService.join(user);

        // then
        assertThatThrownBy(() -> userService.join(user2))
                .isInstanceOf(ExistEmailException.class)
                .hasMessage("이미 사용중인 사용자 이메일 주소 입니다");
    }

    @Test
    @DisplayName("존재하지 않는 사용자 조회를 요청했을때 예외 검증")
    void 존재하지_않는_사용자_검증() {
        // given
        // when
        userService.join(user);

        // then
        assertThatThrownBy(() -> userService.findByUserId("gildong"))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessage("해당 사용자 아이디와 일치하는 사용자가 없습니다.");
    }
}