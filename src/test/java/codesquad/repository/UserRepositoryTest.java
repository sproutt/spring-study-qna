package codesquad.repository;

import codesquad.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("DB에 유저를 저장하는 save메서드 테스트하기")
    void saveUser() throws Exception {
        //given
        User user = new User();
        user.setName("a");
        user.setEmail("a@naver.com");
        user.setUserId("a");
        user.setPassword("a");

        //when
        Long savedId = userRepository.save(user);

        //then
        assertThat(savedId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("DB에 저장되있는 모든 유저들을 조회")
    void findAllUser() throws Exception{
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
        userRepository.save(user);
        userRepository.save(user1);

        //when
        List<User> users = userRepository.findAll();

        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 아이디로 단건 회원 정보를 조회하기")
    void findOne() throws Exception{
        //given
        User user = new User();
        user.setName("a");
        user.setEmail("a@naver.com");
        user.setUserId("a");
        user.setPassword("a");
        userRepository.save(user);

        //when
        User savedUser = userRepository.findById(user.getId());

        //then
        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }
}