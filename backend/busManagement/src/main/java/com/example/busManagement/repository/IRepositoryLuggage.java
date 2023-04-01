package com.example.busManagement.repository;

import com.example.busManagement.domain.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryLuggage extends JpaRepository<Luggage, Long> {
}
