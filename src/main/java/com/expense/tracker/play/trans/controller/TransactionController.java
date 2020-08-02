package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.config.security.service.CustomUserDetails;
import com.expense.tracker.play.trans.payload.TransactionReq.CreateDto;
import com.expense.tracker.play.trans.payload.TransactionReq.UpdateDto;
import com.expense.tracker.play.trans.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @GetMapping
    public ResponseEntity<?> getAllTransactions(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.getAllTransactions(principal.getEmail(), categoryId), HttpStatus.OK);
    }

    /**
     * 트랜잭션 단건 조회
     *
     * @param categoryId
     * @param transactionId
     * @param principal
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable Long categoryId, @PathVariable Long transactionId, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.getTransaction(principal.getEmail(), categoryId, transactionId), HttpStatus.OK);
    }

    /**
     * 트랜잭션 추가
     *
     * @param categoryId
     * @param createDto
     * @param principal
     * @return
     * @throws ResourceNotFoundException
     * @throws UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<?> addTransaction(@PathVariable Long categoryId, @RequestBody CreateDto createDto, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(transactionService.addTransaction(principal.getEmail(), categoryId, createDto), HttpStatus.CREATED);
    }

    /**
     * 트랜잭션 수정
     *
     * @param categoryId
     * @param transactionId
     * @param updateDto
     * @param principal
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @PutMapping(path = "/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long categoryId, @PathVariable Long transactionId, @RequestBody UpdateDto updateDto, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.updateTransaction(principal.getEmail(), categoryId, transactionId, updateDto), HttpStatus.OK);
    }

    /**
     * 트랜잭션 삭제
     *
     * @param categoryId
     * @param transactionId
     * @param principal
     * @return
     */
    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long categoryId, @PathVariable Long transactionId, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        transactionService.deleteTransaction(principal.getEmail(), categoryId, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
