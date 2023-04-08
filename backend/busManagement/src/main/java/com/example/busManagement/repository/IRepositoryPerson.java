package com.example.busManagement.repository;

import com.example.busManagement.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface IRepositoryPerson extends JpaRepository<Person, Long> {

}

