package org.itmo.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParserController {
    @GetMapping("/")
    public void printStat() {

    }
}
