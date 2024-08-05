package rw.global.qt.bloggy.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.global.qt.bloggy.annotations.ValidUUID;
import rw.global.qt.bloggy.dtos.requests.CreateTagDTO;
import rw.global.qt.bloggy.payload.ApiResponse;
import rw.global.qt.bloggy.services.ITagService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import javax.validation.Valid;

import java.util.UUID;

import static rw.global.qt.bloggy.utils.helpers.Helper.logAction;

@Controller
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
@Validated
public class TagController {
    private final ITagService tagService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> findAll(){
        logAction(String.format("Request for getting all tags"));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Tags fetched successfully",tagService.getAllTags()));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTag(@Valid @RequestBody CreateTagDTO createTagDTO){
        logAction(String.format("Request for creating a Tag with Name:  %s", createTagDTO.getName()));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Tag created successfully",tagService.createTag(createTagDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getTagById(@Valid @ValidUUID @PathVariable("id") UUID id){
        logAction(String.format("Request for getting a Tag with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Tag fetched successfully",tagService.getTagById(id)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTagById(@Valid @ValidUUID @PathVariable("id") UUID id){
        logAction(String.format("Request for deleting a Tag with ID:  %s", id));
        try{
            tagService.deleteTag(id);
            return ResponseEntity.ok(new ApiResponse(true,"Tag deleted successfully"));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTag(@Valid @ValidUUID @PathVariable("id") UUID id, @Valid @RequestBody CreateTagDTO createTagDTO){
        logAction(String.format("Request for updating a Tag with ID:  %s", id));
        try{
            return ResponseEntity.ok(new ApiResponse(true,"Tag updated successfully",tagService.updateTag(id,createTagDTO)));
        }catch (Exception e){
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
}
