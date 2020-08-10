package com.expense.tracker.play.permission.repository;

import com.expense.tracker.play.permission.domian.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
