package com.dacdigitals.librarymanagementsystem.controller;

import com.dacdigitals.librarymanagementsystem.dto.PersonDTO;
import com.dacdigitals.librarymanagementsystem.dto.RoleDTO;
import com.dacdigitals.librarymanagementsystem.entity.Person;
import com.dacdigitals.librarymanagementsystem.repository.IPersonRepository;
import com.dacdigitals.librarymanagementsystem.service.IPersonService;
import com.dacdigitals.librarymanagementsystem.utils.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/persons")
public class PersonController {

    private IPersonService  personService;

    @PostMapping("/register")
    public CustomApiResponse<Object> register(@RequestBody @Valid PersonDTO person){
        Person  newPerson = personService.register(person);
        return new CustomApiResponse<>("Registration successful!", newPerson,
                HttpStatus.CREATED);
    }

    @GetMapping
    public CustomApiResponse<Object> getPersons(){
        List<Person> allPersons = personService.getPeople();
        return new CustomApiResponse<>("Success!", allPersons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public CustomApiResponse<Object> getPerson(@PathVariable Long id){
        Person person = personService.getPerson(id);
        return new CustomApiResponse<>("Success!", person, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public CustomApiResponse<Object> getPerson(@PathVariable @Valid PersonDTO person){
        Person updatedPerson = personService.updatePerson(person);
        return new CustomApiResponse<>("Success!", updatedPerson, HttpStatus.OK);
    }

    @PatchMapping("/make-admin/{id}")
    public CustomApiResponse<Object> makeAdmin(@PathVariable Long id,
                                               @RequestBody RoleDTO setRole){
        Person admin = personService.makeAdmin(id, setRole);
        return new CustomApiResponse<>("Person with id number " + admin.getId() + " is now an admin!", admin, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public CustomApiResponse<Object> deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
        return new CustomApiResponse<>("Successfully deleted!", null,
                HttpStatus.OK);
    }
}
