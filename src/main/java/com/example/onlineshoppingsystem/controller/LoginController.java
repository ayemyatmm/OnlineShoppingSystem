package com.example.onlineshoppingsystem.controller;

import com.example.onlineshoppingsystem.models.AppUser;
import com.example.onlineshoppingsystem.models.AppUserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AppUserRepository userRep;
 
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

    @GetMapping("/register")
    public String register(@ModelAttribute("user") AppUser user) {
      return "login/register";
    }
  
    @PostMapping("/register")
    public String createUser(@Validated @ModelAttribute("user") AppUser user, BindingResult result) {
      if (result.hasErrors()) {
        return "login/register";
      }
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRep.save(user);
      // ログイン画面にリダイレクト
      return "redirect:/login?register";
    }
}
