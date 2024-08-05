package rw.global.qt.bloggy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.global.qt.bloggy.models.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID>{
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);

}
