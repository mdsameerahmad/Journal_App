package com.sam.newsapp.Rspository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sam.newsapp.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    void deleteByUsername(String username); 
}
