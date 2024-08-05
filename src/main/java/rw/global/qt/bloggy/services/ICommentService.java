package rw.global.qt.bloggy.services;

import rw.global.qt.bloggy.dtos.requests.CreateCommentDTO;
import rw.global.qt.bloggy.models.Comment;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
    public Comment createComment(CreateCommentDTO comment);
    public Comment createCommentByLoggedInUser(CreateCommentDTO comment);
    public Comment getCommentById(UUID id);
    public Comment updateComment(UUID id, CreateCommentDTO comment);
    public void deleteComment(UUID id);
    public List<Comment> getCommentByBlog(UUID blogId);
    public List<Comment> getCommentByUser(UUID userId);
    public List<Comment> getAllComments();
}
