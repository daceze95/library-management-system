package com.dacdigitals.librarymanagementsystem.repository;

import com.dacdigitals.librarymanagementsystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {

}
