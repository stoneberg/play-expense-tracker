package com.expense.tracker.play.trans.repository;

import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser(User user);
    List<Category> findAllByUserEmail(String email);
    Optional<Category> findByIdAndUser(Long categoryId, User user);
    Optional<Category> findByIdAndUserEmail(Long categoryId, String email);
}
