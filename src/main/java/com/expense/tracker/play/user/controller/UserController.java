package com.expense.tracker.play.user.controller;


import com.expense.tracker.play.common.exception.AuthenticationFailedException;
import com.expense.tracker.play.common.exception.BadRequestException;
import com.expense.tracker.play.common.exception.EmailDuplicationException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.user.payload.UserReq;
import com.expense.tracker.play.user.payload.UserReq.CreateDto;
import com.expense.tracker.play.user.payload.UserReq.LoginDto;
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

    /**
     * 가입
     *
     * @param createDto
     * @return
     * @throws EmailDuplicationException
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateDto createDto) throws EmailDuplicationException {
        return new ResponseEntity<>(userService.registerUser(createDto), HttpStatus.CREATED);
    }

    /**
     * 로그인
     *
     * @param loginDto
     * @return
     * @throws BadRequestException
     * @throws AuthenticationFailedException
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) throws UserNotFoundException, AuthenticationFailedException {
        return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.OK);
    }

}
