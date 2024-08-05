package rw.global.qt.bloggy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.global.qt.bloggy.enums.EGender;
import rw.global.qt.bloggy.enums.EUserStatus;
import rw.global.qt.bloggy.enums.EVisibility;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
//@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@Data
public class User extends Person {

    @Column(name = "password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must be strong")
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    private  String activationCode;
    @Column(name = "visibilty", nullable = false)
    @JsonIgnore
    private EVisibility visibility = EVisibility.VISIBLE;
    @Column(name = "status", nullable = false)
    private EUserStatus accountStatus = EUserStatus.ACTIVE;

    @Column(name = "profile_picture", nullable = true)
    private String profilePicture = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();
    public User(String firstName, String lastName, String email, String username, String activationCode) {
        super(firstName, lastName, email);
        this.username = username;
        this.activationCode = activationCode;
    }
}