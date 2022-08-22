package org.itmo.frontend.controller;

import org.itmo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ParserController {
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/")
    public String printStat(HttpServletRequest request) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @PostMapping("/login")
    public void authorize(@ModelAttribute("username") String username,
                          @ModelAttribute("password") String password,
                          HttpServletResponse response) {
        User user = new User(username, password);
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:9006/login", user, String.class);
        Cookie jwtToken = new Cookie("jwt", result.getHeaders().getFirst("Authorization"));
        response.addCookie(jwtToken);
        response.setHeader("Authorization", result.getHeaders().getFirst("Authorization"))  ;
    }
}
