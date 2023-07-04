package com.ml.hotel.service;

import com.ml.hotel.model.Person;
import com.ml.hotel.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));
    }

    public Person createPerson(Person person) {
        // You can add any additional business logic/validation before saving the person
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        Person person = getPersonById(id);

        // Update the properties of the existing person
        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        person.setEmail(updatedPerson.getEmail());
        // Update any other properties as needed

        // Save the updated person
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        Person person = getPersonById(id);
        personRepository.delete(person);
    }
}
