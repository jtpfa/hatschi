package de.pcmr.shop.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestApiController {
    @RequestMapping(path = "/hello-world", method = RequestMethod.GET)
    public String getHelloWorld() {
        return "Hello World!";
    }
}
