package com.team.case6.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class SignUpForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String username;
    @Size(min = 8, max = 25)
    private String password;
    @NotEmpty
    private String confirmPassword;

    private String name;
    @NotEmpty
    @Email
    private String email;

    private String avatar;

    private String about;

    private String birthDay;

    private String registerDate;

    private String secret;
}
