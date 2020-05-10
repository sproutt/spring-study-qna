package com.greenfrog.qna.domain;

import com.greenfrog.qna.dto.UserDTO;
import com.greenfrog.qna.dto.UserUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.userId = userDTO.getUserId();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
    }


    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }

    public void update(UserUpdateDTO userUpdateDTO) {
        this.password = userUpdateDTO.getNewPassword();
        this.name = userUpdateDTO.getName();
        this.email = userUpdateDTO.getEmail();
    }
}
