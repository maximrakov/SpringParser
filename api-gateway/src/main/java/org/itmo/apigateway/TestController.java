package org.itmo.apigateway;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @PostMapping("/tst")
    public void tst(@RequestBody String username) {
        String p = username;
    }
}
