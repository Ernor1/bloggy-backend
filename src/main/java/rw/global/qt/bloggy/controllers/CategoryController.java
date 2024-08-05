package rw.global.qt.bloggy.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.global.qt.bloggy.annotations.ValidUUID;
import rw.global.qt.bloggy.dtos.requests.CreateCategoryDTO;
import rw.global.qt.bloggy.payload.ApiResponse;
import rw.global.qt.bloggy.services.ICategoryService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import javax.validation.Valid;

import java.util.UUID;

import static rw.global.qt.bloggy.utils.helpers.Helper.logAction;

@Controller
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
@Validated
public class CategoryController {
    private final ICategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAll(){
//        logAction(String.format("Request for getting all categories"));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Categories fetched successfully",categoryService.getAllCategories()));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("all/by-blog/{id}")
    public ResponseEntity<ApiResponse> findAllByBlog(@PathVariable("id") UUID id){
        logAction(String.format("Request for getting all categories by Blog ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Categories fetched successfully",categoryService.getCategoriesByBlog(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO){
        logAction(String.format("Request for creating a Category with Name:  %s", createCategoryDTO.getName()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Category created successfully",categoryService.createCategory(createCategoryDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@Valid @ValidUUID @PathVariable("id") UUID id){
        logAction(String.format("Request for getting a Category with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Category fetched successfully",categoryService.getCategoryById(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@Valid @ValidUUID @PathVariable("id") UUID id){
        logAction(String.format("Request for deleting a Category with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Category deleted successfully",categoryService.getCategoryById(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategoryById(@Valid @ValidUUID @PathVariable("id") UUID id, @Valid @RequestBody CreateCategoryDTO updateCategoryDTO){
        logAction(String.format("Request for updating a Category with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Category updated successfully",categoryService.updateCategory(id,updateCategoryDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
}
