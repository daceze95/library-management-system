package com.dacdigitals.librarymanagementsystem.repository;

import com.dacdigitals.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IBookRepository extends JpaRepository<Book, Long>{
}
