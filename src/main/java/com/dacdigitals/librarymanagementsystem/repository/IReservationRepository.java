package com.dacdigitals.librarymanagementsystem.repository;

import com.dacdigitals.librarymanagementsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,
        Long> {
}
