package rw.global.qt.bloggy.services;

import rw.global.qt.bloggy.dtos.requests.CreateAdminDTO;
import rw.global.qt.bloggy.dtos.requests.CreateUserDTO;
import rw.global.qt.bloggy.enums.Roles;
import rw.global.qt.bloggy.models.Person;
import rw.global.qt.bloggy.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    public  boolean isUserLoggedIn();
    public User getLoggedInUser();
    public User getUserById(UUID uuid);
    public boolean isNotUnique(Person user);
    public boolean validateUserEntry(Person user);
    public List<User> findAll();
    public User findById(UUID id);
    public User saveUser(CreateUserDTO userDto, Roles role);
    public User createAdmin(CreateAdminDTO userDto);
}
