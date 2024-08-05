package rw.global.qt.bloggy.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rw.global.qt.bloggy.dtos.requests.CreateBlogDTO;
import rw.global.qt.bloggy.payload.ApiResponse;
import rw.global.qt.bloggy.services.IBlogService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import javax.validation.Valid;

import static rw.global.qt.bloggy.utils.helpers.Helper.logAction;

@Controller
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
@Validated
public class BlogController {
    private final IBlogService blogService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAll(){
        logAction(String.format("Request for getting all blogs"));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getAllBlogs()));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBlog(@Valid @RequestBody CreateBlogDTO createBlogDTO){
        logAction(String.format("Request for creating a Blog with Title:  %s", createBlogDTO.getTitle()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blog created successfully",blogService.createBlog(createBlogDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

}
