package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.PersonDTO;
import com.dacdigitals.librarymanagementsystem.dto.RoleDTO;
import com.dacdigitals.librarymanagementsystem.entity.Person;

import java.util.List;

public interface IPersonService {
    Person register(PersonDTO person);

    List<Person> getPeople();

    Person getPerson(Long id);

    Person updatePerson(Long id, PersonDTO person);

    Person makeAdmin(Long id, RoleDTO setRole);

    void deletePerson(Long id);
}
