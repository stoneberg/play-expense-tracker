package com.expense.tracker.play.permission.controller;

import com.expense.tracker.play.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admins")
@RestController
public class AdminController {

    private  final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> admins() {
        return new ResponseEntity<>(permissionService.findAdmins(), HttpStatus.CREATED);
    }
}

