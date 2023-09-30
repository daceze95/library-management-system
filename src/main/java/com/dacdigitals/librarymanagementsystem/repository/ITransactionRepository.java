package com.dacdigitals.librarymanagementsystem.repository;

import com.dacdigitals.librarymanagementsystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByBookId(Long bookId);
}
