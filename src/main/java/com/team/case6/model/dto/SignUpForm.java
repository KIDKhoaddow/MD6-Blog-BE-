package com.team.case6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    private String username;

    private String password;

    private String confirmPassword;

    private String name;

    private String email;

    private String avatar;

    private String about;

    private String birthDay;

    private String registerDate;
}
