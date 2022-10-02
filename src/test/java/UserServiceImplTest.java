import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;
import codesquad.repository.ArrayUserRepository;
import codesquad.repository.UserRepository;
import codesquad.service.UserService;
import codesquad.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository = new ArrayUserRepository();

    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository);

    void setUp() {
        UserEntity userEntity = UserEntity.builder()
                .userId("userId")
                .password("password")
                .name("name")
                .email("email@domain.com")
                .build();

        userService.registerUser(userEntity);
    }

    @Test
    void 사용자_등록_테스트() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .userId("wnajsldkf")
                .password("1234")
                .name("jeongin")
                .email("wnajsldkf@naver.com")
                .build();

        // when
        userService.registerUser(userEntity);
        List<UserEntity> userEntityList = userService.findUsers();

        // then
        Assertions.assertEquals(userEntityList.get(0).getUserId(), userEntity.getUserId());
    }

    @Test
    void 사용자_정보_수정_테스트() {
        // given
        setUp();
        UserUpdateRequestDto testUserUpdateRequestDto = UserUpdateRequestDto.builder()
                .password("qwer")
                .name("july")
                .email("1234@naver.com")
                .build();

        // when
        userService.updateUser("userId", testUserUpdateRequestDto);
        UserEntity testUserEntity = userService.findUser("userId");

        // then
        Assertions.assertEquals(testUserEntity.getName(), testUserUpdateRequestDto.getName());
    }

    @Test
    void 특정_사용자_불러오기_테스트() {
        // given
        setUp();

        // when
        UserEntity userEntity = userService.findUser("userId");

        // then
        Assertions.assertEquals(userEntity.getEmail(), "email@domain.com");
    }

    @Test
    void 모든_사용자_불러오기() {
        // given
        setUp();

        // when
        List<UserEntity> userEntityList = userService.findUsers();

        // then
        Assertions.assertEquals(userEntityList.size(), 1);
    }
}
