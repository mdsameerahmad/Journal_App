package com.sam.newsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.newsapp.Services.UserServices;
import com.sam.newsapp.entity.Users;

@RestController
@RequestMapping("/public")
public class PublicController {
   @Autowired
    private UserServices us;
@PostMapping("/create-user")
    public ResponseEntity<Users> createUsers(@RequestBody Users saveUser) {
        try {
            Users saved = us.SaveNewUser(saveUser);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace(); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
