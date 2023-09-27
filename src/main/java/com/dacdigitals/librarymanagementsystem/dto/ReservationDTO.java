package com.dacdigitals.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReservationDTO {
    @NotNull(message = "user id cannot be empty!")
    private long userId;
    @NotNull(message = "please provide a book id!")
    private long bookId;
}
