package rw.global.qt.bloggy.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.global.qt.bloggy.dtos.requests.CreateAdminDTO;
import rw.global.qt.bloggy.dtos.requests.CreateUserDTO;
import rw.global.qt.bloggy.enums.Roles;
import rw.global.qt.bloggy.payload.ApiResponse;
import rw.global.qt.bloggy.services.serviceImpls.UserServiceImpl;
import rw.global.qt.bloggy.utils.ExceptionUtils;
import javax.validation.Valid;
import java.util.UUID;
import static rw.global.qt.bloggy.utils.helpers.Helper.logAction;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
@Validated
public class UserController {
    private final UserServiceImpl userService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserDTO createUserDTO, @RequestParam("role") Roles role) {
        try {
//            logAction(String.format("Request for creating a student with Full name :  %s , and email  :  %s", createUserDTO.getFirstName() + createUserDTO.getLastName() , createUserDTO.getEmail()));
            return ResponseEntity.ok().body(new ApiResponse(
                            true,
                            "User created successfully",
                            userService.saveUser(createUserDTO,role)
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

    @PostMapping("/create-admin")
    public ResponseEntity<ApiResponse> createAdmin(@Valid @RequestBody CreateAdminDTO createAdminDTO) {
        try {
//            logAction(String.format("Request for creating a student with Full name :  %s , and email  :  %s", createAdminDTO.getFirstName() + createAdminDTO.getLastName() , createCustomerDTO.getEmail()));
            return ResponseEntity.ok().body(new ApiResponse(
                            true,
                            "Admin created successfully",
                            userService.createAdmin(createAdminDTO)
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse> getAllUsers(){
        try {
            logAction(String.format("Request for getting all users"));
            return ResponseEntity.ok().body(new ApiResponse(
                            true,
                            "Users fetched successfully",
                            userService.findAll()
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") UUID id){
        try {
            logAction(String.format("Request for getting a user with id : %s", id));
            return ResponseEntity.ok().body(new ApiResponse(
                            true,
                            "User fetched successfully",
                            userService.findById(id)
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
}

