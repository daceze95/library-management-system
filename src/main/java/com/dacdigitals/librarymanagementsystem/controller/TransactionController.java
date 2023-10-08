package com.dacdigitals.librarymanagementsystem.controller;

import com.dacdigitals.librarymanagementsystem.dto.TransactionDTO;
import com.dacdigitals.librarymanagementsystem.entity.Transaction;
import com.dacdigitals.librarymanagementsystem.service.ITransactionService;
import com.dacdigitals.librarymanagementsystem.utils.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping("/borrow")
    public CustomApiResponse<Object> borrowBook(@RequestBody @Valid TransactionDTO transaction) {
        Transaction res = transactionService.borrowBook(transaction);
        return new CustomApiResponse<>("Successful", res, HttpStatus.OK);
    }

    @PostMapping("/return")
    public CustomApiResponse<Object> returnBook(@RequestParam Long transactionId) {
        Transaction res = transactionService.returnBook(transactionId);
        return new CustomApiResponse<>("Successful", res, HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
    public CustomApiResponse<Object> cancelBorrow(@RequestParam Long transactionId) {
        String res = transactionService.cancelBorrow(transactionId);
        return new CustomApiResponse<>("Successful", res, HttpStatus.OK);
    }

    @GetMapping("/transaction")
    public CustomApiResponse<Object> getTransactionById(@RequestParam(
            "transaction_id") Long transactionId) {
        Transaction response =
                transactionService.getTransactionById(transactionId);

        return new CustomApiResponse<>("retrieved successfully!", response,
                HttpStatus.OK);
    }

    @GetMapping
    public CustomApiResponse<Object> getAllTransaction() {
        List<Transaction> res = transactionService.getAllTransaction();
        return new CustomApiResponse<>("Success!", res, HttpStatus.OK);
    }

}
