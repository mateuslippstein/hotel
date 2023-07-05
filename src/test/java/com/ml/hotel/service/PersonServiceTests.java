package com.ml.hotel.service;

import com.ml.hotel.exception.DuplicatedDocumentException;
import com.ml.hotel.model.Person;
import com.ml.hotel.repository.PersonRepository;
import com.ml.hotel.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PersonServiceTests {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPersons() {
        // Prepare
        List<Person> persons = Arrays.asList(
                new Person(1L, "John", "Doe", "john@example.com", "123456789"),
                new Person(2L, "Jane", "Smith", "jane@example.com", "987654321")
        );
        when(personRepository.findAll()).thenReturn(persons);

        // Execute
        List<Person> result = personService.getAllPersons();

        // Verify
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(persons, result);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testGetPersonById() {
        // Prepare
        Person person = new Person(1L, "John", "Doe", "john@example.com", "123456789");
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Execute
        Person result = personService.getPersonById(1L);

        // Verify
        Assertions.assertEquals(person, result);
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreatePerson() {
        // Prepare
        Person person = new Person(1L, "John", "Doe", "john@example.com", "123456789");
        when(personRepository.save(person)).thenReturn(person);

        // Execute
        Person result = personService.createPerson(person);

        // Verify
        Assertions.assertEquals(person, result);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testCreatePerson_DuplicateDocument() {
        // Prepare
        Person person = new Person(1L, "John", "Doe", "john@example.com", "123456789");
        when(personRepository.save(person)).thenThrow(DataIntegrityViolationException.class);

        // Execute and Verify
        Assertions.assertThrows(DuplicatedDocumentException.class, () -> personService.createPerson(person));
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testUpdatePerson() {
        // Prepare
        Person existingPerson = new Person(1L, "John", "Doe", "john@example.com", "123456789");
        Person updatedPerson = new Person(1L, "John", "Smith", "john@example.com", "123456789");
        when(personRepository.findById(1L)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(existingPerson)).thenReturn(updatedPerson);

        // Execute
        Person result = personService.updatePerson(1L, updatedPerson);

        // Verify
        Assertions.assertEquals(updatedPerson, result);
       verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).save(existingPerson);
    }

    @Test
    public void testDeletePerson() {
        // Prepare
        Person person = new Person(1L, "John", "Doe", "john@example.com", "123456789");
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        // Execute
        personService.deletePerson(1L);

        // Verify
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    public void testDeletePerson_NotFound() {
        // Prepare
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute and Verify
        Assertions.assertThrows(EntityNotFoundException.class, () -> personService.deletePerson(1L));
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, never()).delete(any());
    }
}
