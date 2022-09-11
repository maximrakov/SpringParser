package org.itmo.auth.controller;

import org.itmo.auth.entity.ApplicationUser;
import org.itmo.auth.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthController {

    private RestTemplate restTemplate;
    private ApplicationUserService applicationUserService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(ApplicationUserService applicationUserService, RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.applicationUserService = applicationUserService;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void register(@RequestBody ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserService.saveUser(user);
        restTemplate.exchange("http://localhost:9007/register",
                HttpMethod.POST, new HttpEntity<ApplicationUser>(user), String.class);

        restTemplate.exchange("http://localhost:9003/register",
                HttpMethod.POST, new HttpEntity<ApplicationUser>(user), String.class);

        restTemplate.exchange("http://localhost:9001/register",
                HttpMethod.POST, new HttpEntity<ApplicationUser>(user), String.class);

    }

    @GetMapping("/get/{username}")
    public ApplicationUser get(@PathVariable String username) {
        return applicationUserService.loadUserByUsername(username);
    }
}
