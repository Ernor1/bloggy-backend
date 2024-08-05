package rw.global.qt.bloggy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.global.qt.bloggy.models.Tag;

import java.util.Optional;
import java.util.UUID;

public interface ITagRepository extends JpaRepository<Tag, UUID> {
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %?1%")
    public Optional<Tag> findByName(String name);
}
