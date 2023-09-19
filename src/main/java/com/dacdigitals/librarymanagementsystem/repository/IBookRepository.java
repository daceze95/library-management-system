package com.dacdigitals.librarymanagementsystem.repository;

import com.dacdigitals.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBookRepository extends JpaRepository<Book, Long>{
}
