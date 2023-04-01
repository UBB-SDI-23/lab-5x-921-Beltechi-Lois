package com.example.busManagement.repository;

import com.example.busManagement.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryPassenger extends JpaRepository<Passenger, Long> {

}

