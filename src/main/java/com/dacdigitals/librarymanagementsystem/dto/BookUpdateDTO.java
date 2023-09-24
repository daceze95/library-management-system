package com.dacdigitals.librarymanagementsystem.dto;

import com.dacdigitals.librarymanagementsystem.entity.constant.GENRE;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDTO {

    @NotNull(message = "id cannot be empty!")
    private Long id;
    @NotNull(message = "title cannot be empty!")
    private String title;
    @NotNull(message = "author cannot be empty!")
    private String author;
    @NotNull(message = "genre cannot be empty!")
    private GENRE category;
    @NotNull(message = "publication year cannot be empty!")
    private String yearOfPublication;
    @NotNull(message = "description cannot be empty!")
    private String description;
    @NotNull(message = "available field cannot be empty! Enter (true or " +
            "false).")
    private Boolean available;
}
