package rw.global.qt.bloggy.services.serviceImpls;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.dtos.requests.CreateBlogDTO;
import rw.global.qt.bloggy.exceptions.ResourceNotFoundException;
import rw.global.qt.bloggy.models.Blog;
import rw.global.qt.bloggy.models.Category;
import rw.global.qt.bloggy.models.Tag;
import rw.global.qt.bloggy.models.User;
import rw.global.qt.bloggy.repositories.IBlogRepository;
import rw.global.qt.bloggy.repositories.ICategoryRepository;
import rw.global.qt.bloggy.repositories.ITagRepository;
import rw.global.qt.bloggy.repositories.IUserRepository;
import rw.global.qt.bloggy.services.IBlogService;
import rw.global.qt.bloggy.services.IUserService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor

public class BlogServiceImpl implements IBlogService {
    private final IBlogRepository blogRepository;
    private final IUserRepository userRepository;
    private final ICategoryRepository categoryRepository;
    private final ITagRepository tagRepository;
    private final IUserService userService;
    @Override
    public Blog createBlog(CreateBlogDTO blog) {
        try {
            User author = userRepository.findById(blog.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Set<Category> categories= new HashSet<>();
            Set<Tag> tags= new HashSet<>();
            for (UUID categoryId:blog.getCategories()){
                categories.add(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found")));
            }
            for (UUID tagId:blog.getTags()){
                tags.add(tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found")));
            }
            Blog newBlog = new Blog(blog.getTitle(), blog.getContent(),author);
            newBlog.setCategories(categories);
            newBlog.setTags(tags);
            return blogRepository.save(newBlog);
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Blog createBlogByLoggedInUser(CreateBlogDTO blog) {
        try {
            User author= userService.getLoggedInUser();
            Set<Category> categories=new HashSet<>();
            Set<Tag> tags=new HashSet<>();
            for (UUID categoryId:blog.getCategories()){
                categories.add(categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found")));
            }
            for (UUID tagId:blog.getTags()){
                tags.add(tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found")));
            }
            Blog newBlog = new Blog(blog.getTitle(), blog.getContent(),author);
            newBlog.setCategories(categories);
            newBlog.setTags(tags);
            return blogRepository.save(newBlog);
        }catch (Exception e){
            e.printStackTrace();
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Blog getBlogById(UUID id) {
        try {
            return blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
    @Override
    public Blog updateBlog(UUID id, CreateBlogDTO blog) {
       try {
              Blog blogToUpdate = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
              blogToUpdate.setTitle(blog.getTitle());
              blogToUpdate.setContent(blog.getContent());
              return blogRepository.save(blogToUpdate);
       }catch (Exception e){
           ExceptionUtils.handleServiceExceptions(e);
           return null;
       }
    }

    @Override
    public void deleteBlog(UUID id) {
        try {
            Blog blogToDelete = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
            blogRepository.delete(blogToDelete);
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
        }

    }

    @Override
    public List<Blog> getAllBlogs() {
        try {
            return blogRepository.findAll();
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
    @Override
    public List<Blog> getBlogByTitle(String title) {
        try {
            return blogRepository.findAllByTitle(title);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Blog> getBlogByCategory(UUID categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            return blogRepository.findAllByCategoriesContains(category);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Blog> getBlogByTag(UUID tagId) {
        try {
            return blogRepository.findAllByTagsContains(tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found")));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Blog> getBlogByAuthor(String author) {
        try {
            return blogRepository.findAllByAuthor(userRepository.findUserByUsername(author).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Blog> getBlogByPublished(boolean published) {
        try {
            return blogRepository.findAllByPublished(published);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
}
