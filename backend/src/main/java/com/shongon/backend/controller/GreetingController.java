package com.shongon.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class GreetingController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }
}
