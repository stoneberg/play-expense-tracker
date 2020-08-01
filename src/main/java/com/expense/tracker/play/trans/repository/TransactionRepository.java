package com.expense.tracker.play.trans.repository;

import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory(Category category);

    Optional<Transaction> findByIdAndCategory(Long transactionId, Category category);

    @Query("select sum(t.amount) from Transaction t left join t.category c where c.id = :categoryId")
    Double selectTotalExpense(@Param("categoryId") Long categoryId);
}
