package com.example.busManagement.repository;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.BusRoutesAveragePeopleDTO;
import com.example.busManagement.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRepositoryBusRoute extends JpaRepository<Bus_Route, Long> {
//    @Query("SELECT new com.example.busManagement.domain.DTO.BusRoutesAveragePeopleDTO("
//            + "br.id, br.bus_name, br.route_type, CAST(COUNT(t.person) AS LONG))"
//            + "FROM Bus_Route br JOIN br.tickets t WHERE t.person IS NOT NULL "
//            + "GROUP BY br.id, br.bus_name, br.route_type "
//            + "ORDER BY COUNT(t.person) DESC")
//    List<BusRoutesAveragePeopleDTO> findTop50BusRoutesOrderedByPeopleTransported();
}
