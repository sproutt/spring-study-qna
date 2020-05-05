package com.greenfrog.qna.service;

import com.greenfrog.qna.domain.User;
import com.greenfrog.qna.dto.UserUpdateDTO;
import com.greenfrog.qna.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {

    UserService userService;

    UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void updateUserTest() {
        User user = new User();
        user.setId(1l);
        user.setUserId("imbf");
        user.setName("배종진");
        user.setPassword("123");
        user.setEmail("whdwls9052@naver.com");

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName("기표");
        userUpdateDTO.setCurrentPassword("123");
        userUpdateDTO.setNewPassword("123123");
        userUpdateDTO.setEmail("Gipyoo@naver.com");

        //Given
        given(userRepository.findById(1l)).willReturn(Optional.of(user));

        //when
        boolean ok = userService.updateUser( 1, userUpdateDTO);

        //then
        assertThat(ok).isTrue();
        assertThat(user.getName()).isEqualTo("기표");
        assertThat(user.getPassword()).isEqualTo("123123");
        assertThat(user.getEmail()).isEqualTo("Gipyoo@naver.com");
    }

}