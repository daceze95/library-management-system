package com.dacdigitals.librarymanagementsystem.dto;

import com.dacdigitals.librarymanagementsystem.entity.constant.TYPE;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @NotNull
    @Min(value = 1, message = "field cannot be empty or less than 1!")
    private long userId;
    @NotNull
    @Min(value = 1, message = "field cannot be empty or less than 1!")
    private long bookId;
    private TYPE type;
}
