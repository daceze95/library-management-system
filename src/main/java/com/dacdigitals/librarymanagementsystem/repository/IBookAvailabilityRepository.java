package com.dacdigitals.librarymanagementsystem.repository;

import com.dacdigitals.librarymanagementsystem.entity.BookAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookAvailabilityRepository extends JpaRepository<BookAvailability, Long> {
    BookAvailability findByBookId(Long bookId);
}
