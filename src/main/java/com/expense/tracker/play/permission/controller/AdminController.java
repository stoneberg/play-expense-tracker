package com.expense.tracker.play.permission.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class AdminController {
    @GetMapping
    public String admin() {
        return "You are admin and here now!";
    }
}

