package codesquad.service;

import codesquad.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원 저장됐는지 확인하기")
    void save() throws Exception{
        //given
        User user = new User();
        user.setName("a");
        user.setEmail("a@naver.com");
        user.setUserId("a");
        user.setPassword("a");

        //when
        Long savedId = userService.join(user);

        //then
        assertThat(savedId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("모든 유저 조회하기")
    void findAll() throws Exception{
        //given
        User user = new User();
        user.setName("a");
        user.setEmail("a@naver.com");
        user.setUserId("a");
        user.setPassword("a");
        User user1 = new User();
        user1.setName("b");
        user1.setEmail("b@naver.com");
        user1.setUserId("b");
        user1.setPassword("b");

        userService.join(user);
        userService.join(user1);

        //when
        List<User> users = userService.findUsers();

        //then
        assertThat(users.size()).isEqualTo(2);
    }

}