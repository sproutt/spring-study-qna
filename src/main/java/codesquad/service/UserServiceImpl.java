package codesquad.service;

import codesquad.dto.user.UserUpdateRequestDto;
import codesquad.entity.UserEntity;
import codesquad.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserEntity user) {
        userRepository.register(user);
    }

    @Override
    public void updateUser(String userId, UserUpdateRequestDto requestDto) {
        userRepository.update(userId, requestDto);
    }

    @Override
    public UserEntity findUser(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }
}
