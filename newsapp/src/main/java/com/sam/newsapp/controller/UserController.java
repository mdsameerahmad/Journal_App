package com.sam.newsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.newsapp.ApiResponse.WeatherResponse;
import com.sam.newsapp.Repository.UserRepository;
import com.sam.newsapp.Services.UserServices;
import com.sam.newsapp.Services.WeatherServices;
import com.sam.newsapp.entity.Users;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    // private UserServices us;
    private UserServices userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherServices weatherServices;

    @PutMapping
    public ResponseEntity<?> updateUsers(@RequestBody Users users) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
        Users userInDB = userService.findByUsername(Username);
        userInDB.setUsername(users.getUsername());
        userInDB.setPassword(users.getPassword());
        userService.SaveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping

    public ResponseEntity<?> deleteUseryById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping

    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherServices.getWeather("Goa");
        String greeting = "";
        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            WeatherResponse.Current current = weatherResponse.getCurrent();
            greeting = ", Today's Weather is : "
                    + current.getTempC() + "°C, "
                    + current.getFeelslikeC() + "°C, "
                    + current.getWindKph() + " kph, "
                    + current.getCondition().getText();
        }

        return new ResponseEntity<>("Hy " + authentication.getName() + greeting, HttpStatus.OK);
    }

}
