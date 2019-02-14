package codesquad.net.slipp.web.service;


import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void checkIdPasswordTest() {
        User user = new User();
        user.setUserId("test");
        user.setPassword("123");
        assertThat(userService.checkIdPassword(user)).isFalse();

        user.setPassword("1234");
        assertThat(userService.checkIdPassword(user)).isTrue();
    }

    @Test(expected = UserNotFoundException.class)
    public void checkIdPasswordExceptionTest() {
        User user = new User();
        user.setUserId("testE");
        user.setPassword("1234");
        userService.checkIdPassword(user);
    }

    @Test
    public void updateTest() {
        User modelUser = userService.findById(Long.parseLong("2"));
        User modifiedUser = new User();
        modifiedUser.setUserId("test");
        modifiedUser.setEmail("changedTest@gamil.com");
        modifiedUser.setName("바뀜");
        modifiedUser.setPassword("1234");
        userService.update(modelUser, modifiedUser, "1232");

        assertThat(userService.findByUserId("test").getEmail()).isEqualTo("changedTest@gamil.com");
    }

}