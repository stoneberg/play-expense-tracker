package com.expense.tracker.play.user.repository;


import com.expense.tracker.play.user.domain.ERole;
import com.expense.tracker.play.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
