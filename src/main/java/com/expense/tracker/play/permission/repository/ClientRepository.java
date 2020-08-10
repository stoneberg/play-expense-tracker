package com.expense.tracker.play.permission.repository;

import com.expense.tracker.play.permission.domian.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
