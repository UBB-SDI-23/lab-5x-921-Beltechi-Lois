package com.example.busManagement.repository;

import com.example.busManagement.domain.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRepositoryLuggage extends JpaRepository<Luggage, Long> {

    @Query("SELECT l FROM Luggage l WHERE l.person.id = :personId")
    List<Luggage> findLuggagesByPersonId(@Param("personId") Long personId);
    @Query("SELECT COUNT(l) FROM Luggage l WHERE l.person.id = :personId")
    int countByPersonId(@Param("personId") Long personId);
}
