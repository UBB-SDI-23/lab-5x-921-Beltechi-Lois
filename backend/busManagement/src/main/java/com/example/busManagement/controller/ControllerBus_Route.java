package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.BusRouteDTOwithTicketsFK;
import com.example.busManagement.domain.DTO.BusRoutesAveragePeopleDTO;
import com.example.busManagement.domain.DTO.Bus_Route_PeopleDTO;
import com.example.busManagement.domain.DTO.PersonWithTicketDTO;
import com.example.busManagement.exception.BusRouteNotFoundException;

import com.example.busManagement.repository.IRepositoryBusRoute;

import com.example.busManagement.repository.IRepositoryTicket;
import com.example.busManagement.service.ServiceBus_Route;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
class ControllerBus_Route {

    private final ServiceBus_Route busRouteService;



    ControllerBus_Route(ServiceBus_Route busRouteService) {
        this.busRouteService = busRouteService;
    }


    //GETALL fara tickets ; no People information ok,No_Of_People_Transported
    @GetMapping("/busroutes")
    @CrossOrigin(origins = "*")
    List<Bus_Route_PeopleDTO> all() {

        return busRouteService.getAllbusroutes();
    }

    //GET BY ID, fara TICKETS; cu Person & SeatNumber with Date
    @GetMapping("/busroutes/{id}")
    @CrossOrigin(origins = "*")
    BusRouteDTOwithTicketsFK one(@PathVariable String id) {

        return busRouteService.getByIdbusroutes(id);
    }

    // A2 FILTER
    @GetMapping("/busroutes/higherThanGivenDistance/{value}")
    @CrossOrigin(origins = "*")
    public List<Bus_Route> higherThan(@PathVariable String value) {
        return busRouteService.filterHigherThanGivenDistance(value);
    }

    @PostMapping("/busroutes")   // ADD
    Bus_Route newBusRoute(@Valid @RequestBody Bus_Route newBusRoute) {
        return busRouteService.addBusRoute(newBusRoute);
    }

    @PutMapping("/busroutes/{id}")   //UPDATE
    Bus_Route replaceBusRoute(@Valid @RequestBody Bus_Route newBus_Route, @PathVariable Long id) {

        return busRouteService.updateBusRoute(newBus_Route, id);
    }

    @DeleteMapping("/busroutes/{id}")   //DELETE
    void deleteBusRoute(@PathVariable Long id) {

        busRouteService.deleteBusRoute(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @GetMapping("/busroutes/order-people-transported")
    @CrossOrigin(origins = "*")
    public List<BusRoutesAveragePeopleDTO> getBusRoutesOrderedByPeopleTransported() {
        return busRouteService.getBusRoutesOrderedByPeopleTransported();
    }



}
