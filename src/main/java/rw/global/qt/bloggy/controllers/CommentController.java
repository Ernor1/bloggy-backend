package rw.global.qt.bloggy.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.global.qt.bloggy.annotations.ValidUUID;
import rw.global.qt.bloggy.dtos.requests.CreateCommentDTO;
import rw.global.qt.bloggy.payload.ApiResponse;
import rw.global.qt.bloggy.services.ICommentService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import javax.validation.Valid;

import java.util.UUID;

import static rw.global.qt.bloggy.utils.helpers.Helper.logAction;

@Controller
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
@Validated
public class CommentController {
    private final ICommentService commentService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAll(){
//        logAction(String.format("Request for getting all comments"));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comments fetched successfully",commentService.getAllComments()));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO){
        logAction(String.format("Request for creating a Comment with Content:  %s", createCommentDTO.getContent()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comment created successfully",commentService.createComment(createCommentDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/createByLoggedInUser")
    public ResponseEntity<ApiResponse> createCommentByLoggedInUser(@Valid @RequestBody CreateCommentDTO createCommentDTO){
        logAction(String.format("Request for creating a Comment by logged in user with Content:  %s", createCommentDTO.getContent()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comment created successfully",commentService.createCommentByLoggedInUser(createCommentDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getCommentById(  @PathVariable("id") UUID id){
        logAction(String.format("Request for getting a Comment with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comment fetched successfully",commentService.getCommentById(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCommentById(  @PathVariable("id") UUID id){
        logAction(String.format("Request for deleting a Comment with ID:  %s", id));
        try{
            commentService.deleteComment(id);
            return ResponseEntity.ok(new ApiResponse(true,"Comment deleted successfully"));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateComment(  @PathVariable("id") UUID id, @Valid @RequestBody CreateCommentDTO createCommentDTO){
        logAction(String.format("Request for updating a Comment with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comment updated successfully",commentService.updateComment(id, createCommentDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/getByBlog/{id}")
    public ResponseEntity<ApiResponse> getCommentsByBlogId( @PathVariable("id") UUID id){
//        logAction(String.format("Request for getting all comments of a Blog with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comments fetched successfully",commentService.getCommentByBlog(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/getByUser/{id}")
    public ResponseEntity<ApiResponse> getCommentsByUserId(  @PathVariable("id") UUID id){
        logAction(String.format("Request for getting all comments of a User with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Comments fetched successfully",commentService.getCommentByUser(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

}
