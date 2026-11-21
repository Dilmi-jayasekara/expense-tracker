package com.uni.expense_tracker.service;

import com.uni.expense_tracker.dto.CategorySummary;
import com.uni.expense_tracker.dto.ExpenseRequest;
import com.uni.expense_tracker.model.Category;
import com.uni.expense_tracker.model.Expense;
import com.uni.expense_tracker.model.User;
import com.uni.expense_tracker.repository.CategoryRepository;
import com.uni.expense_tracker.repository.ExpenseRepository;
import com.uni.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Expense createExpense(String email, ExpenseRequest request) {
        // 1. Find the user by the email from the JWT token
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Find the category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 3. Build the expense
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setNote(request.getNote());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setUser(user);
        expense.setCategory(category);

        // 4. Save to Neon DB
        return expenseRepository.save(expense);
    }

    public List<Expense> getUserExpenses(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUserId(user.getId());
    }

    // The missing method you just added
    public List<CategorySummary> getMonthlySummary(String email, int month, int year) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.getSummaryByMonth(user.getId(), month, year);
    }
}