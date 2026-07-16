package com.lorekeeper.lorekeeper_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String greet()
    {
        return "hello this is me";
    }
}
