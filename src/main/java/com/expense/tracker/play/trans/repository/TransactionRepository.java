package com.expense.tracker.play.trans.repository;

import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory(Category category);
    Optional<Transaction> findByIdAndCategory(Long transactionId, Category category);
}
