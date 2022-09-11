package org.itmo.telegramservice;

import org.itmo.telegramservice.entity.ApplicationUser;
import org.itmo.telegramservice.service.ApplicationUserService;
import org.itmo.telegramservice.service.TgUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgController {
    private TgUserDAO tgUserDAO;
    private ApplicationUserService applicationUserService;

    @Autowired
    public TgController(TgUserDAO tgUserDAO, ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
        this.tgUserDAO = tgUserDAO;
    }

    @PostMapping("/register")
    public void register(@RequestBody ApplicationUser applicationUser) {
        applicationUserService.save(applicationUser);
    }
    @GetMapping("/")
    public void get() {
        tgUserDAO.save("ivan", 113);
    }

    @GetMapping("/id")
    public int getId() {
        return tgUserDAO.getChatId("ivan");
    }
}
