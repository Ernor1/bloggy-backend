package rw.global.qt.bloggy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.global.qt.bloggy.models.Blog;
import rw.global.qt.bloggy.models.Comment;
import rw.global.qt.bloggy.models.User;

import java.util.List;
import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByBlog(Blog blog);
    List<Comment> findAllByUser(User user);
}
