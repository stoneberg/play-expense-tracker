package com.expense.tracker.play.user.controller;


import com.expense.tracker.play.common.exception.AuthenticationFailedException;
import com.expense.tracker.play.common.exception.EmailDuplicationException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.common.exception.UsernameNotFoundException;
import com.expense.tracker.play.user.payload.UserReq;
import com.expense.tracker.play.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserReq.CreateUserDto reqDto) throws EmailDuplicationException {
        log.info("@registerUser.userDto=========>{}", reqDto);
        return new ResponseEntity<>(userService.registerUser(reqDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserReq.LoginUserDto reqDto) throws UsernameNotFoundException, AuthenticationFailedException {
        log.info("@loginUser.userDto=========>{}", reqDto);
        return new ResponseEntity<>(userService.loginUser(reqDto), HttpStatus.OK);
    }

}
