package rw.global.qt.bloggy.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import rw.global.qt.bloggy.models.Role;

import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Role findByRoleName(String roleName);
}
