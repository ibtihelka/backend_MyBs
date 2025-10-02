package com.smldb2.demo.controllers;


import com.smldb2.demo.DTO.UnifiedLoginResponse;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "**")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UnifiedLoginResponse> login(@RequestBody User loginRequest) {
        UnifiedLoginResponse response = authService.login(
                loginRequest.getPersoId(),
                loginRequest.getPersoPassed()
        );

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
}

