package com.sam.newsapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.newsapp.Services.UserServices;
import com.sam.newsapp.entity.Users;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private UserServices userServices;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<Users> allUsers = userServices.getAllUsers();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-Admin")
    public void CreateAdmin(@RequestBody Users admin) {
        admin.setRoles(List.of("ADMIN"));
        userServices.SaveAdmin(admin);
        logger.info("Successfully created admin user: " + admin.getUsername());

    }

}
