package com.expense.tracker.play.trans.repository;

import com.expense.tracker.play.common.querydsl.Querydsl4RepositorySupport;
import com.expense.tracker.play.trans.domain.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionQuerydslRepository extends Querydsl4RepositorySupport {

    public TransactionQuerydslRepository() {
        super(Transaction.class);
    }

}
