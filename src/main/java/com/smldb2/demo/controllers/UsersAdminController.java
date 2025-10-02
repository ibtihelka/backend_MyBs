package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.UsersAdmin;
import com.smldb2.demo.services.UsersAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "**")
public class UsersAdminController {

    @Autowired
    private UsersAdminService usersAdminService;

    @GetMapping
    public ResponseEntity<List<UsersAdmin>> getAllUsersAdmin() {
        List<UsersAdmin> admins = usersAdminService.getAllUsersAdmin();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersAdmin> getUserAdminById(@PathVariable String id) {
        return usersAdminService.getUserAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UsersAdmin> getUserAdminByName(@PathVariable String name) {
        return usersAdminService.getUserAdminByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
