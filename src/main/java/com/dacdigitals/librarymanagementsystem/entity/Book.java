package com.dacdigitals.librarymanagementsystem.entity;

import com.dacdigitals.librarymanagementsystem.entity.constant.GENRE;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String author;
    private UUID ISBN; //subject to change
    private GENRE category;
    private String yearOfPublication;
    private String description;
    private Boolean available;
    private LocalDateTime dateCreated;
    private LocalDateTime updatedAt;

}
