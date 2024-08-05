package rw.global.qt.bloggy.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rw.global.qt.bloggy.models.Role;
import rw.global.qt.bloggy.models.User;


import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    public String token;
    public User user;
    public Set<Role> roles;
}
