package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.payload.TransactionReq.CreateDto;
import com.expense.tracker.play.trans.payload.TransactionReq.UpdateDto;
import com.expense.tracker.play.trans.service.TransactionService;
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
     * @param session
     * @return
     * @throws UserNotFoundException
     */
    @GetMapping
    public ResponseEntity<?> getAllTransactions(@PathVariable("categoryId") Long categoryId, HttpSession session) throws UserNotFoundException, ResourceNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(transactionService.getAllTransactions(email, categoryId), HttpStatus.OK);
    }

    /**
     * 트랜잭션 단건 조회
     *
     * @param categoryId
     * @param transactionId
     * @param session
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable("categoryId") Long categoryId, @PathVariable("transactionId") Long transactionId, HttpSession session) throws UserNotFoundException, ResourceNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(transactionService.getTransaction(email, categoryId, transactionId), HttpStatus.OK);
    }


    /**
     * 트랜잭션 추가
     * 
     * @param categoryId
     * @param createDto
     * @param session
     * @return
     * @throws ResourceNotFoundException
     * @throws UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<?> addTransaction(@PathVariable Long categoryId, @RequestBody CreateDto createDto, HttpSession session) throws ResourceNotFoundException, UserNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(transactionService.addTransaction(email, categoryId, createDto), HttpStatus.CREATED);
    }

    /**
     * 트랜잭션 수정
     *
     * @param categoryId
     * @param transactionId
     * @param updateDto
     * @param session
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @PutMapping(path = "/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable("categoryId") Long categoryId, @PathVariable("transactionId") Long transactionId, @RequestBody UpdateDto updateDto, HttpSession session) throws UserNotFoundException, ResourceNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(transactionService.updateTransaction(email, categoryId, transactionId, updateDto), HttpStatus.OK);
    }

    /**
     * 트랜잭션 삭제
     *
     * @param categoryId
     * @param transactionId
     * @param session
     * @return
     */
    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("categoryId") Long categoryId, @PathVariable("transactionId") Long transactionId, HttpSession session) throws ResourceNotFoundException, UserNotFoundException {
        String email = (String) session.getAttribute("email");
        transactionService.deleteTransaction(email, categoryId, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
