package org.itmo.telegramservice;

import org.itmo.telegramservice.service.TgUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgController {
    private TgUserDAO tgUserDAO;

    @Autowired
    public TgController(TgUserDAO tgUserDAO) {
        this.tgUserDAO = tgUserDAO;
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
