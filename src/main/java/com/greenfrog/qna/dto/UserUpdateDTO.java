package com.greenfrog.qna.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private String currentPassword;
    private String newPassword;
    private String name;
    private String email;

}
