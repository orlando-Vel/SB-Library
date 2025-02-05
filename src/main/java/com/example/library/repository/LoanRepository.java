package com.example.library.repository;

import com.example.library.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser(User user);
    List<Loan> findByBook(Book book);
    List<Loan> findByReturnedFalse();
}


