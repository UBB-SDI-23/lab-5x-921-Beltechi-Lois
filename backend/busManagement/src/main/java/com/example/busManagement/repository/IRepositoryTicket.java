package com.example.busManagement.repository;

import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryTicket extends JpaRepository<Ticket, Long> {
}
