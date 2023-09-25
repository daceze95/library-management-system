package com.dacdigitals.librarymanagementsystem.entity;

import com.dacdigitals.librarymanagementsystem.entity.constant.ROLE;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // will be encrypted!
    private LocalDate dateOfBirth;
    private ROLE role;
    private LocalDateTime dateCreated;
    private LocalDateTime updatedAt;
}
