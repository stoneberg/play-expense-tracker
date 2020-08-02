package com.expense.tracker.play.permission.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/client")
@RestController
public class ClientController {
    @GetMapping
    public String client() {
        return "You are client and here now!";
    }
}

