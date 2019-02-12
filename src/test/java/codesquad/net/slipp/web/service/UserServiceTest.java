package codesquad.net.slipp.web.service;


import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void 로그인_실패_비밀번호(){
        User user = new User();
        user.setUserId("test");
        user.setPassword("123");

        assertThat(userService.checkLogin(user)).isFalse();
    }

    @Test
    public void 로그인_성공(){
        User user = new User();
        user.setUserId("test");
        user.setPassword("1234");

        assertThat(userService.checkLogin(user)).isTrue();
    }

    @Test(expected = UserNotFoundException.class)
    public void 로그인_실패_아이디(){
        User user = new User();
        user.setUserId("testE");
        user.setPassword("1234");
        userService.checkLogin(user);
    }
}