package rw.global.qt.bloggy.services;

import org.springframework.http.ResponseEntity;
import rw.global.qt.bloggy.dtos.requests.LoginDTO;
import rw.global.qt.bloggy.payload.ApiResponse;


import java.util.UUID;

public interface AuthenticationService {
    ResponseEntity<ApiResponse> login(LoginDTO dto);
    ResponseEntity<ApiResponse> verifyAccount(String email, String code);


    ResponseEntity<ApiResponse> getProfileById(UUID id);

    ResponseEntity<ApiResponse> getProfile();



}
