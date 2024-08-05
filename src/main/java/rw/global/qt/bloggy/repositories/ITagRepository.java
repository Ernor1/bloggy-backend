package rw.global.qt.bloggy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.global.qt.bloggy.models.Tag;

import java.util.UUID;

public interface ITagRepository extends JpaRepository<Tag, UUID> {
}
