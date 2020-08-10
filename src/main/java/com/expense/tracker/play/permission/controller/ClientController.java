package com.expense.tracker.play.permission.controller;

import com.expense.tracker.play.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RequiredArgsConstructor
@RequestMapping("/api/clients")
@RestController
public class ClientController {

    private  final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<?> clients() {
        return new ResponseEntity<>(permissionService.findClients(), HttpStatus.CREATED);
    }
}

