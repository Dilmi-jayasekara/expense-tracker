package com.uni.expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    private BigDecimal amount;
    private String note;
    private LocalDate expenseDate;
    private Long categoryId;

    // Getters and Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}