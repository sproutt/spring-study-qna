package com.greenfrog.qna.service;

import com.greenfrog.qna.domain.User;
import com.greenfrog.qna.dto.UserUpdateDTO;
import com.greenfrog.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void signUp(User user) {
        userRepository.save(user);
    }

    public User findUserById(int id) {
        return userRepository.findById((long) id).orElseThrow(NoSuchElementException::new);
    }

    public boolean updateUser(int id, UserUpdateDTO userUpdateDTO) {
        User user = this.findUserById(id);
        if (user.isSamePassword(userUpdateDTO.getCurrentPassword())) {
            user.update(userUpdateDTO);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
