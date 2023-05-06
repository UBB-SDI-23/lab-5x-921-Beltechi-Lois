package com.example.busManagement.repository;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.BusRoutesAveragePeopleDTO;
import com.example.busManagement.domain.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface IRepositoryBusRoute extends JpaRepository<Bus_Route, Long> {

    @Query("SELECT b FROM Bus_Route b WHERE CAST(b.distance AS INTEGER) > :value")
    List<Bus_Route> findByDistanceGreaterThan(@Param("value") int value, PageRequest pageRequest);


    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.bus_route.id = :busRouteId")
    int countTicketsByBusRouteId(@Param("busRouteId") Long busRouteId);

//    @Query("SELECT r, COUNT(t) FROM Bus_Route r JOIN r.tickets t GROUP BY r.id")
//    List<Object[]> countTicketsByBusRouteIdUsingJoin(long offset, int limit);



//    @Query("SELECT new com.example.busManagement.domain.DTO.BusRoutesAveragePeopleDTO("
//            + "br.id, br.bus_name, br.route_type, CAST(COUNT(t.person) AS LONG))"
//            + "FROM Bus_Route br JOIN br.tickets t WHERE t.person IS NOT NULL "
//            + "GROUP BY br.id, br.bus_name, br.route_type "
//            + "ORDER BY COUNT(t.person) DESC")
//    List<BusRoutesAveragePeopleDTO> findTop50BusRoutesOrderedByPeopleTransported();
}
