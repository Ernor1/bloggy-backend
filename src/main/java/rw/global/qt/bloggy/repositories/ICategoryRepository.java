package rw.global.qt.bloggy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.global.qt.bloggy.models.Category;

import java.util.Optional;
import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<Category, UUID>{
    Optional<Category> findByName(String name);

}
