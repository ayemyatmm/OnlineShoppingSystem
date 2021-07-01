package com.example.onlineshoppingsystem.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.onlineshoppingsystem.models.AppUser;
import com.example.onlineshoppingsystem.models.AppUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyUserService implements UserDetailsService {
  
   private final AppUserRepository rep;
   @Override
   public UserDetails loadUserByUsername(String username)
       throws UsernameNotFoundException {
       AppUser usr = rep.findByUsername(username);
       if (usr != null) {
           return new User(username, usr.getPassword(),
               Collections.emptySet());
       } else {
           throw new UsernameNotFoundException("User does not exist.");
       }
   }
}