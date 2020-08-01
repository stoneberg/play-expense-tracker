package com.expense.tracker.play.trans.service;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.domain.Transaction;
import com.expense.tracker.play.trans.payload.TransactionReq;
import com.expense.tracker.play.trans.payload.TransactionReq.CreateDto;
import com.expense.tracker.play.trans.payload.TransactionReq.UpdateDto;
import com.expense.tracker.play.trans.payload.TransactionRes;
import com.expense.tracker.play.trans.payload.TransactionRes.FindDto;
import com.expense.tracker.play.trans.repository.CategoryRepository;
import com.expense.tracker.play.trans.repository.TransactionRepository;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    /**
     * 트랜잭션 목록 조회
     * 
     * @param email
     * @param categoryId
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    public List<FindDto> getAllTransactions(String email, Long categoryId) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));

        List<Transaction> transactions = transactionRepository.findByCategory(category);
        return transactions.stream().map(FindDto::new).collect(Collectors.toList());
    }

    /**
     * 트랜잭션 단건 조회
     * 
     * @param email
     * @param categoryId
     * @param transactionId
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    public FindDto getTransaction(String email, Long categoryId, Long transactionId) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));
        Transaction transaction = transactionRepository.findByIdAndCategory(transactionId, category)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", transactionId)));

        return TransactionRes.FindDto.toDto(transaction);
    }

    /**
     * 트랜잭션 추가
     * 
     * @param email
     * @param categoryId
     * @param createDto
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @Transactional
    public Long addTransaction(String email, Long categoryId, CreateDto createDto) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));

        Transaction transaction = Transaction.createTransaction(user, category, createDto);
        transactionRepository.save(transaction);

        return transaction.getId();
    }

    /**
     * 트랜잭션 수정
     * 
     * @param email
     * @param categoryId
     * @param transactionId
     * @param updateDto
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @Transactional
    public Long updateTransaction(String email, Long categoryId, Long transactionId, UpdateDto updateDto) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));
        Transaction transaction = transactionRepository.findByIdAndCategory(transactionId, category)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", transactionId)));

        // update by dirty checking
        transaction.updateTransaction(updateDto);
        return transaction.getId();
    }

    /**
     * 트랜잰션 삭제
     * 
     * @param email
     * @param categoryId
     * @param transactionId
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @Transactional
    public void deleteTransaction(String email, Long categoryId, Long transactionId) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));
        Transaction transaction = transactionRepository.findByIdAndCategory(transactionId, category)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", transactionId)));

        transactionRepository.delete(transaction);
    }

}
