package com.dacdigitals.librarymanagementsystem.dto;

import com.dacdigitals.librarymanagementsystem.entity.constant.ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotNull(message = "First Name field cannot be empty!")
    private String firstName;
    @NotNull(message = "Last Name field cannot be empty!")
    private String lastName;
    @NotNull(message = "Email field cannot be empty!")
    @Email(message = "Please enter a valid email!")
    @Pattern(regexp = "\\w+@gmail\\.com|\\w+@yahoo\\.com|\\w+@hotmail\\.com$")
    private String email;
    @NotNull(message = "Please enter a password!")
    @Size(min = 4, max = 12, message = "Password must contain min of 4 or max" +
            " of 12 characters!")
    private String password;
    @NotNull(message = "Date of birth cannot be empty!")
    private LocalDate dateOfBirth;
}
