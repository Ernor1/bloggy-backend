package rw.global.qt.bloggy.security.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rw.global.qt.bloggy.models.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserSecurityDetails implements UserDetails {
    public String username;
    public String password;
    @Getter
    public List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    public UserSecurityDetails(User user){
        this.username = user.getEmail();
        this.password = user.getPassword();
        user.getRoles().forEach(role -> {
            UserAuthority userAuthority = new UserAuthority(user.getId() ,  role.getRoleName());
            grantedAuthorities.add(userAuthority);
        });
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
