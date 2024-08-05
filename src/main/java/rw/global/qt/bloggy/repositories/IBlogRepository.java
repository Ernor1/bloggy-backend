package rw.global.qt.bloggy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.global.qt.bloggy.models.Blog;
import rw.global.qt.bloggy.models.Category;
import rw.global.qt.bloggy.models.Tag;
import rw.global.qt.bloggy.models.User;

import java.util.List;
import java.util.UUID;

public interface IBlogRepository extends JpaRepository<Blog, UUID>{
    @Query("SELECT b FROM Blog b WHERE b.title like  %?1%")
    List<Blog> findAllByTitle(String title);
    List<Blog> findAllByCategoriesContains(Category category);
    List<Blog> findAllByTagsContains(Tag tag);
    List<Blog> findAllByPublished(boolean published);
    List<Blog> findAllByAuthor(User user);
}
