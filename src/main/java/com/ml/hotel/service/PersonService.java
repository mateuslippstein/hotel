package com.ml.hotel.service;

import com.ml.hotel.exception.DuplicatedDocumentException;
import com.ml.hotel.model.Person;
import com.ml.hotel.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Retrieves all persons.
     *
     * @return a list of all persons.
     */
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Retrieves a person by their ID.
     *
     * @param id the ID of the person to retrieve.
     * @return the person with the specified ID.
     * @throws EntityNotFoundException if the person with the given ID does not exist.
     */
    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));
    }

    /**
     * Creates a new person.
     *
     * @param person the person to create.
     * @return the created person.
     * @throws DuplicatedDocumentException if a person with the same document already exists.
     */
    public Person createPerson(Person person) {
        try {
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedDocumentException("Document already exists: " + person.getDocument());
        }
    }

    /**
     * Updates an existing person.
     *
     * @param id            the ID of the person to update.
     * @param updatedPerson the updated person information.
     * @return the updated person.
     * @throws EntityNotFoundException if the person with the given ID does not exist.
     */
    public Person updatePerson(Long id, Person updatedPerson) {
        Person person = getPersonById(id);

        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        person.setEmail(updatedPerson.getEmail());

        return personRepository.save(person);
    }

    /**
     * Deletes a person by their ID.
     *
     * @param id the ID of the person to delete.
     * @throws EntityNotFoundException if the person with the given ID does not exist.
     */
    public void deletePerson(Long id) {
        Person person = getPersonById(id);
        personRepository.delete(person);
    }
}
