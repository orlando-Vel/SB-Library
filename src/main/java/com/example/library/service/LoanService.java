package com.example.library.service;

import com.example.library.model.Loan;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan createLoan(Long bookId, Long userId) {
        Book book = bookService.getBookById(bookId)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        User user = userService.getUserById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(14)); // 2 semanas de préstamo
        return loanRepository.save(loan);
    }

    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        loan.setReturned(true);
        loanRepository.save(loan);
    }

    public List<Loan> getLoansByUser(Long userId) {
        User user = userService.getUserById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return loanRepository.findByUser(user);
    }

    public List<Loan> getLoansByBook(Long bookId) {
        Book book = bookService.getBookById(bookId)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return loanRepository.findByBook(book);
    }
}