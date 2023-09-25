package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.PersonDTO;
import com.dacdigitals.librarymanagementsystem.dto.RoleDTO;
import com.dacdigitals.librarymanagementsystem.entity.Person;
import com.dacdigitals.librarymanagementsystem.entity.constant.ROLE;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.NoContentException;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.NoPersonFoundException;
import com.dacdigitals.librarymanagementsystem.repository.IPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {

    public final IPersonRepository personRepository;

    @Override
    public Person register(PersonDTO person) {
        final ROLE personRole = ROLE.USER;
        LocalDateTime createdDate = LocalDateTime.now();
        Person newPerson = Person.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .password(person.getPassword())
                .dateOfBirth(person.getDateOfBirth())
                .role(personRole)
                .dateCreated(createdDate)
                .updatedAt(createdDate)
                .build();
        return personRepository.save(newPerson);
    }

    @Override
    public List<Person> getPeople() {
        List<Person> people = personRepository.findAll();
        if (people.isEmpty()) {
            throw new NoContentException("Nothing Found!");
        }
        return people;
    }

    @Override
    public Person getPerson(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            return person.get();
        } else {
            throw new NoPersonFoundException("Person with id number " + id +
                    " not found!");
        }
    }

    @Override
    public Person updatePerson(Long id, PersonDTO person) {
        LocalDateTime updatedAt = LocalDateTime.now();
        Optional<Person> retrievedPerson = personRepository.findById(id);
        System.out.println(retrievedPerson.toString());
        if (retrievedPerson.isPresent()) {
            Person pesn = retrievedPerson.get();
            Person updatedPerson = Person.builder()
                    .id(pesn.getId())
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .email(person.getEmail())
                    .password(person.getPassword())
                    .dateOfBirth(person.getDateOfBirth())
                    .role(pesn.getRole())
                    .dateCreated(pesn.getDateCreated())
                    .updatedAt(updatedAt)
                    .build();
            return personRepository.save(updatedPerson);
        } else {
            throw new NoPersonFoundException("Person with id " + id +
                    " not found!");
        }
    }

    @Override
    public Person makeAdmin(Long id, RoleDTO setRole) {
        LocalDateTime updatedAt = LocalDateTime.now();
        Optional<Person> retrievedPerson = personRepository.findById(id);
        System.out.println(retrievedPerson.toString());
        if (retrievedPerson.isPresent()) {
            Person pesn = retrievedPerson.get();
            pesn.setRole(setRole.getRole());
            pesn.setUpdatedAt(updatedAt);

            return personRepository.save(pesn);
        } else {
            throw new NoPersonFoundException("Person with id " + id +
                    " not found!");
        }

    }

    @Override
    public void deletePerson(Long id) {
        Person pesn;
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            pesn = person.get();
            personRepository.delete(pesn);
        } else {
            throw new NoPersonFoundException("Person with id number " + id +
                    " doesn't exist!");
        }
    }
}
