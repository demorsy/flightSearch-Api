package com.demorsy.Flight.Search.API.repos;

import com.demorsy.Flight.Search.API.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport,String> {
}
