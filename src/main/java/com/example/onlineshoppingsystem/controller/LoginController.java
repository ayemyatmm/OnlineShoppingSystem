package com.example.onlineshoppingsystem.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
 
    @GetMapping("/login")
    public String login(Model model) {
        return "login/index";
    }

    @GetMapping({"/password", "/password/{pass}"})
    @ResponseBody
    public String password(@RequestParam(defaultValue = "12345") String pass) {
         BCryptPasswordEncoder enc = new BCryptPasswordEncoder() ;
         return enc.encode(pass);
    }
}
