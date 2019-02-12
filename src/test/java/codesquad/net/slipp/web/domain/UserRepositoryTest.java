package codesquad.net.slipp.web.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void dataBaseTest(){
        User user = new User();
        user.setUserId("test");
        user.setEmail("testUser@gmail.com");
        user.setPassword("1234");

        User newUser = userRepository.save(user);
        assertThat(newUser).isNotNull();

        Optional<User> existingUser = userRepository.findByUserId("test");
        assertThat(existingUser).isNotEmpty();

        String userId = existingUser.get().getUserId();
        assertThat(userId).isEqualTo("test");
    }
}