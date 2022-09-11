package org.itmo.frontend.controller;

import org.itmo.entity.ApplicationUser;
import org.itmo.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserRestController {

    ApplicationUserService applicationUserService;

    @Autowired
    public ParserRestController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/register")
    public void register(@RequestBody ApplicationUser applicationUser) {
        applicationUserService.save(applicationUser);
    }
}
