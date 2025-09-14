package com.sam.newsapp.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sam.newsapp.Rspository.UserRepository;
import com.sam.newsapp.entity.Users;

@Component
public class UserServices {

    @Autowired
    private UserRepository userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Users SaveNewUser(Users UserEntry) {
        UserEntry.setPassword(passwordEncoder.encode(UserEntry.getPassword()));
        UserEntry.setRoles(Arrays.asList("USER")); // Assign default role
        return userRepo.save(UserEntry);
    }

    public Users SaveAdmin(Users admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRoles(Arrays.asList("USER", "ADMIN")); // Assign admin role
        return userRepo.save(admin);
    }

    public Users SaveUser(Users UserEntry) {
        return userRepo.save(UserEntry);
    }

    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<Users> findById(Long id) {
        return userRepo.findById(id);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public Users findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}
