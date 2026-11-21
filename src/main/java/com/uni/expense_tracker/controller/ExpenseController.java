package com.uni.expense_tracker.controller;

import com.uni.expense_tracker.dto.CategorySummary;
import com.uni.expense_tracker.dto.ExpenseRequest;
import com.uni.expense_tracker.model.Expense;
import com.uni.expense_tracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseRequest request, Authentication authentication) {
        String email = authentication.getName();
        Expense savedExpense = expenseService.createExpense(email, request);
        return ResponseEntity.ok(savedExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getMyExpenses(Authentication authentication) {
        String email = authentication.getName();
        List<Expense> expenses = expenseService.getUserExpenses(email);
        return ResponseEntity.ok(expenses);
    }

    // The new summary endpoint
    @GetMapping("/summary")
    public ResponseEntity<List<CategorySummary>> getSummary(
            @RequestParam int month,
            @RequestParam int year,
            Authentication authentication) {

        String email = authentication.getName();
        List<CategorySummary> summary = expenseService.getMonthlySummary(email, month, year);
        return ResponseEntity.ok(summary);
    }
}