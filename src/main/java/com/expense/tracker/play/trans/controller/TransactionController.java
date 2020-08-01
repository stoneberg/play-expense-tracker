package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.annotations.LoginUser;
import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.payload.TransactionReq.CreateDto;
import com.expense.tracker.play.trans.payload.TransactionReq.UpdateDto;
import com.expense.tracker.play.trans.service.TransactionService;
import com.expense.tracker.play.user.domain.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    /**
     * 트랜잭션 목록 조회
     *
     * @param categoryId
     * @param user
     * @return
     * @throws UserNotFoundException
     */
    @GetMapping
    public ResponseEntity<?> getAllTransactions(@PathVariable("categoryId") Long categoryId, @LoginUser UserSession user) throws UserNotFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.getAllTransactions(user.getEmail(), categoryId), HttpStatus.OK);
    }

    /**
     * 트랜잭션 단건 조회
     *
     * @param categoryId
     * @param transactionId
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable("categoryId") Long categoryId, @PathVariable("transactionId") Long transactionId, @LoginUser UserSession user) throws UserNotFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.getTransaction(user.getEmail(), categoryId, transactionId), HttpStatus.OK);
    }


    /**
     * 트랜잭션 추가
     * 
     * @param categoryId
     * @param createDto
     * @param user
     * @return
     * @throws ResourceNotFoundException
     * @throws UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<?> addTransaction(@PathVariable Long categoryId, @RequestBody CreateDto createDto, @LoginUser UserSession user) throws ResourceNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(transactionService.addTransaction(user.getEmail(), categoryId, createDto), HttpStatus.CREATED);
    }

    /**
     * 트랜잭션 수정
     *
     * @param categoryId
     * @param transactionId
     * @param updateDto
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @PutMapping(path = "/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable("categoryId") Long categoryId, @PathVariable("transactionId") Long transactionId, @RequestBody UpdateDto updateDto, @LoginUser UserSession user) throws UserNotFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.updateTransaction(user.getEmail(), categoryId, transactionId, updateDto), HttpStatus.OK);
    }

    /**
     * 트랜잭션 삭제
     *
     * @param categoryId
     * @param transactionId
     * @param user
     * @return
     */
    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("categoryId") Long categoryId, @PathVariable("transactionId") Long transactionId, @LoginUser UserSession user) throws ResourceNotFoundException, UserNotFoundException {
        transactionService.deleteTransaction(user.getEmail(), categoryId, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
