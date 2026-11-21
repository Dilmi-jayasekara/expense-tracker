package com.uni.expense_tracker.repository;

import com.uni.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically writes the SQL for this method!
    Optional<User> findByEmail(String email);
}