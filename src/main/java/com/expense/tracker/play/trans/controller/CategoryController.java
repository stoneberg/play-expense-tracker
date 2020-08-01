package com.expense.tracker.play.trans.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final HttpServletRequest request;

    @GetMapping("")
    public String getAllCategories() {
        HttpSession httpSession = request.getSession(false);
        String email = (String) httpSession.getAttribute("email");
        String username = (String) httpSession.getAttribute("username");
        return username + StringUtils.SPACE + email;
    }
}
