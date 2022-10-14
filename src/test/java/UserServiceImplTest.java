import codesquad.entity.UserEntity;
import codesquad.repository.UserRepositoryImpl;
import codesquad.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepositoryImpl userRepository;

    @Test
    void 사용자_등록_테스트() {
        UserEntity userEntity = userList().get(0);

        doThrow(RuntimeException.class).when(userRepository).register(userEntity);
        Assertions.assertThrows(RuntimeException.class, () -> userService.registerUser(userEntity));
    }

    @Test
    void 사용자_정보_수정_테스트() {
        UserEntity userEntity = userList().get(0);

        doThrow(RuntimeException.class).when(userRepository).update("july", userEntity);
        Assertions.assertThrows(RuntimeException.class, () -> userService.updateUser("july", userEntity));
    }

    @Test
    void 특정_사용자_불러오기_테스트() {
        // given
        doReturn(userList().get(0)).when(userRepository)
                .findById("july");

        // when
        UserEntity userEntity = userService.findUser("july");

        // then
        Assertions.assertEquals(userEntity.getEmail(), "test@mail.com");
    }


    @Test
    void 모든_사용자_불러오기() {
        // given
        doReturn(userList()).when(userRepository)
                .findAll();

        // when
        List<UserEntity> userList = userService.findUsers();

        // then
        Assertions.assertEquals(userList.size(), 1);
    }

    List<UserEntity> userList(){
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity("july", "123", "jeongin", "test@mail.com"));
        return userList;
    }
}
