package com.sam.newsapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sam.newsapp.Rspository.UserRepository;
import com.sam.newsapp.entity.Users;

@Service
@Component
public class UserDetailServiceImp implements UserDetailsService {


   @Autowired
   private UserRepository userRepository;

 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users users = userRepository.findByUsername(username);
       if(users !=null){
       UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
        .username(users.getUsername())
        .password(users.getPassword())
        .roles(users.getRoles().toArray(new String[0]))
        .build();
        return userDetails;
       }
        throw new UsernameNotFoundException("User Not Found");
    }
}
