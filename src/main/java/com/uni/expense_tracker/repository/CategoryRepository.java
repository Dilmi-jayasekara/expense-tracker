package com.uni.expense_tracker.repository;

import com.uni.expense_tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Find all categories belonging to a specific user
    List<Category> findByUserId(Long userId);
}