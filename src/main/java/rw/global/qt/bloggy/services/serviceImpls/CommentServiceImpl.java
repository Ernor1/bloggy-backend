package rw.global.qt.bloggy.services.serviceImpls;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.dtos.requests.CreateCommentDTO;
import rw.global.qt.bloggy.exceptions.ResourceNotFoundException;
import rw.global.qt.bloggy.models.Blog;
import rw.global.qt.bloggy.models.Comment;
import rw.global.qt.bloggy.models.User;
import rw.global.qt.bloggy.repositories.IBlogRepository;
import rw.global.qt.bloggy.repositories.ICommentRepository;
import rw.global.qt.bloggy.repositories.IUserRepository;
import rw.global.qt.bloggy.services.ICommentService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IBlogRepository blogRepository;
    @Override
    public Comment createComment(CreateCommentDTO comment) {
        try{
            User user = userRepository.findById(comment.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Comment newComment = new Comment(comment.getContent(), user);
            return commentRepository.save(newComment);
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }

    }

    @Override
    public Comment getCommentById(UUID id) {
        try {
            return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Comment updateComment(UUID id, CreateCommentDTO comment) {
        try {
            Comment commentToUpdate = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
            commentToUpdate.setContent(comment.getContent());
            return commentRepository.save(commentToUpdate);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public void deleteComment(UUID id) {
        try {
            Comment commentToDelete = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
            commentRepository.delete(commentToDelete);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
        }

    }

    @Override
    public List<Comment> getCommentByBlog(UUID blogId) {
        try {
            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
            return commentRepository.findAllByBlog(blog);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Comment> getCommentByUser(UUID userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return commentRepository.findAllByUser(user);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Comment> getAllComments() {
        try {
            return commentRepository.findAll();
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
}
