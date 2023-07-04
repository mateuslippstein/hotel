package com.ml.hotel.repository;

import com.ml.hotel.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Add any custom query methods if needed
}
