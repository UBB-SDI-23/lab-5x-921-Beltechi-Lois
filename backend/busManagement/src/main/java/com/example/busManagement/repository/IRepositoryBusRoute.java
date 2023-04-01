package com.example.busManagement.repository;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryBusRoute extends JpaRepository<Bus_Route, Long> {
}
