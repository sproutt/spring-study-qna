package com.greenfrog.qna.domain;

import com.greenfrog.qna.dto.UserUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }

    public void update(UserUpdateDTO userUpdateDTO) {
        this.password = userUpdateDTO.getNewPassword();
        this.name = userUpdateDTO.getName();
        this.email = userUpdateDTO.getEmail();
    }
}
