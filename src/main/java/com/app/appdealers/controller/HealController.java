package com.app.appdealers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealController {

    @GetMapping("/healt")
    public String healt() {
        return "OK!";
    }

}
