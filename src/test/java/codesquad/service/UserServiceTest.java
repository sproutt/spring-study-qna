package codesquad.service;

import codesquad.repository.UserRepository;
import codesquad.util.HttpSessionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private MockHttpSession session;

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUpSession() {
        userService = new UserServiceImpl(userRepository);
        session = new MockHttpSession();
        session.setAttribute("sessionedUser", userService.findUser(Integer.toUnsignedLong(1)));
    }

    @Test
    public void create() {
        String userId = "qwe";
        userService.create(userId, "123", "seobseob", "seob@naver.com");
        assertNotNull(userService.findUser(userId));
    }

    @Test
    public void findUsers() {
        assertNotNull(userService.findUsers());
    }

    @Test
    public void findUser() {
        assertNotNull(userService.findUser("test"));
    }

    @Test
    public void findUser1() {
        assertNotNull(userService.findUser(Integer.toUnsignedLong(1)));
    }

    @Test
    public void isSessionedUser() throws Exception {
        assertNotNull(HttpSessionUtils.getUserFromSession(session));
    }

    @Test
    public void getLoginUser() {
        assertNotNull(userService.getLoginUser(Integer.toUnsignedLong(1), session));
    }

    @Test
    public void checkPassword() {
        assertTrue(userService.checkPassword("123", session));
    }

    @Test
    public void checkPassword1() {
        assertTrue(userService.checkPassword("test", "123"));
    }

    @Test
    public void updateUser() {
        userService.updateUser("123", "min", "min@gmail.com", session);
        assertEquals("123", HttpSessionUtils.getUserFromSession(session).getPassword());
        assertEquals("min", HttpSessionUtils.getUserFromSession(session).getName());
        assertEquals("min@gmail.com", HttpSessionUtils.getUserFromSession(session).getEmail());
    }
}