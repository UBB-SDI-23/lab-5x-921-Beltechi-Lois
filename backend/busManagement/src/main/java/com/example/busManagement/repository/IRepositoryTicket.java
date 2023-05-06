package com.example.busManagement.repository;

import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRepositoryTicket extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.person.id = :personId")
    List<Ticket> findByPersonId(@Param("personId") Long personId);


    @Query("SELECT t FROM Ticket t WHERE t.bus_route.id = :busRouteId")
    List<Ticket> findByBusRouteId(@Param("busRouteId") Long busRouteId);

//    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.bus_route.id = :busRouteId")
//    int countTicketsByBusRouteId(@Param("busRouteId") Long busRouteId);

}
