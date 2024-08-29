package com.crud.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSLController {
    @GetMapping("/api/secure/hello")
    public String hello(){
        return "Hello, Secure World!";
    }
}
