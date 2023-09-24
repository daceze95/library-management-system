package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.PersonDTO;
import com.dacdigitals.librarymanagementsystem.dto.RoleDTO;
import com.dacdigitals.librarymanagementsystem.entity.Person;
import com.dacdigitals.librarymanagementsystem.repository.IPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService{
    public IPersonRepository personRepository;

    @Override
    public Person register(PersonDTO person) {
        return null;
    }

    @Override
    public List<Person> getPeople() {
        return null;
    }

    @Override
    public Person getPerson(Long id) {
        return null;
    }

    @Override
    public Person updatePerson(PersonDTO person) {
        return null;
    }

    @Override
    public Person makeAdmin(Long id, RoleDTO setRole) {
        return null;
    }

    @Override
    public void deletePerson(Long id) {

    }
}
