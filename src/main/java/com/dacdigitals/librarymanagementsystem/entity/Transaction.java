package com.dacdigitals.librarymanagementsystem.entity;

import com.dacdigitals.librarymanagementsystem.entity.constant.TYPE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    private long bookId;
    @Enumerated(value = EnumType.STRING)
    private TYPE type;
    private LocalDateTime dueDate;
    private LocalDateTime trxnDate;
    private LocalDateTime returnDate;
}
