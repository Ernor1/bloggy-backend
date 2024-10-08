package rw.global.qt.bloggy.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.global.qt.bloggy.annotations.ValidUUID;
import rw.global.qt.bloggy.dtos.requests.CreateBlogDTO;
import rw.global.qt.bloggy.payload.ApiResponse;
import rw.global.qt.bloggy.services.IBlogService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import javax.validation.Valid;

import java.util.UUID;

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
//        logAction(String.format("Request for getting all blogs"));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getAllBlogs()));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR','USER')")
    public ResponseEntity<ApiResponse> createBlog(@Valid @RequestBody CreateBlogDTO createBlogDTO,@RequestParam("file") MultipartFile file){
        logAction(String.format("Request for creating a Blog with Title:  %s", createBlogDTO.getTitle()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blog created successfully",blogService.createBlog(createBlogDTO,file)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/create/by-logged-in-user")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR','USER')")
    public ResponseEntity<ApiResponse> createBlogByLoggedInUser( @ModelAttribute CreateBlogDTO createBlogDTO,@RequestParam("file") MultipartFile file){
        logAction(String.format("Request for creating a Blog with Title:  %s", createBlogDTO.getTitle()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blog created successfully",blogService.createBlogByLoggedInUser(createBlogDTO,file)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getBlogById( @PathVariable("id") UUID id){
//        logAction(String.format("Request for getting a Blog with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blog fetched successfully",blogService.getBlogById(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR','USER')")
    public ResponseEntity<ApiResponse> deleteBlogById(@PathVariable("id") UUID id){
        logAction(String.format("Request for deleting a Blog with ID:  %s", id));
        try{
            blogService.deleteBlog(id);
            return ResponseEntity.ok(new ApiResponse(true,"Blog deleted successfully"));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR','USER')")
    public ResponseEntity<ApiResponse> updateBlog(@PathVariable("id") UUID id, @Valid @RequestBody CreateBlogDTO createBlogDTO,@RequestParam("file") MultipartFile file){
        logAction(String.format("Request for updating a Blog with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blog updated successfully",blogService.updateBlog(id,createBlogDTO,file)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/by-author/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR','USER')")
    public ResponseEntity<ApiResponse> getBlogsByAuthor(@PathVariable("name")  String name){
        logAction(String.format("Request for getting all blogs by author with ID:  %s", name));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getBlogByAuthor(name)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/by-category/{id}")
    public ResponseEntity<ApiResponse> getBlogsByCategory(@PathVariable("id") UUID id){
//        logAction(String.format("Request for getting all blogs by category with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getBlogByCategory(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/by-tag/{id}")
    public ResponseEntity<ApiResponse> getBlogsByTag(@PathVariable("id") UUID id){
//        logAction(String.format("Request for getting all blogs by tag with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getBlogByTag(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/by-title/{title}")
    public ResponseEntity<ApiResponse> getBlogsByTitle( @PathVariable("title") String title){
//        logAction(String.format("Request for getting all blogs by title:  %s", title));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getBlogByTitle(title)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/by-published/{published}")
    public ResponseEntity<ApiResponse> getBlogsByPublished( @PathVariable("published") boolean published){
//        logAction(String.format("Request for getting all blogs by published:  %s", published));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Blogs fetched successfully",blogService.getBlogByPublished(published)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
}
