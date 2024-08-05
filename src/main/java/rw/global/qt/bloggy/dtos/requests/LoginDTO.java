package rw.global.qt.bloggy.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    String email;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    String password;
}
