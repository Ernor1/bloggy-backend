package rw.global.qt.bloggy.services;

import rw.global.qt.bloggy.dtos.requests.CreateBlogDTO;
import rw.global.qt.bloggy.models.Blog;

import java.util.List;
import java.util.UUID;

public interface IBlogService {
    public Blog createBlog(CreateBlogDTO blog);
    public Blog createBlogByLoggedInUser(CreateBlogDTO blog);
    public Blog getBlogById(UUID id);
    public Blog updateBlog(UUID id, CreateBlogDTO blog);
    public void deleteBlog(UUID id);
    public List<Blog> getAllBlogs();
    public List<Blog> getBlogByTitle(String title);
    public List<Blog> getBlogByCategory(UUID category);
    public List<Blog> getBlogByTag(UUID tag);
    public List<Blog> getBlogByAuthor(UUID author);
    public List<Blog> getBlogByPublished(boolean published);
}
