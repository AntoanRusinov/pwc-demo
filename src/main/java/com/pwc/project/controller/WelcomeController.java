package com.pwc.project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public String welcomePage() {
        return "Welcome to the " + appName + " page";
    }

}