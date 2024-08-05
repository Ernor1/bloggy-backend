package rw.global.qt.bloggy.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.global.qt.bloggy.annotations.ValidDateOfBirth;
import rw.global.qt.bloggy.annotations.ValidGender;
import rw.global.qt.bloggy.enums.EGender;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotNull
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotNull
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;


}