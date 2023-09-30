package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.TransactionDTO;
import com.dacdigitals.librarymanagementsystem.entity.Transaction;

import java.util.List;

public interface ITransactionService {
    Transaction borrowBook(TransactionDTO transaction);

    Transaction returnBook(Long transactionId);

    String cancelBorrow(Long transactionId);

    Transaction getTransactionByBookId(Long bookId);

    Transaction getTransactionById(Long transactionId);

    List<Transaction> getAllTransaction();
}
