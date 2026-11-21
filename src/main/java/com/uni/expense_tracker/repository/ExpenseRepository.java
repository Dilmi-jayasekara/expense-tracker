package com.uni.expense_tracker.repository;

import com.uni.expense_tracker.dto.CategorySummary;
import com.uni.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    @Query("SELECT new com.uni.expense_tracker.dto.CategorySummary(c.name, SUM(e.amount)) " +
            "FROM Expense e JOIN e.category c " +
            "WHERE e.user.id = :userId AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year " +
            "GROUP BY c.name")
    List<CategorySummary> getSummaryByMonth(@Param("userId") Long userId,
                                            @Param("month") int month,
                                            @Param("year") int year);
}