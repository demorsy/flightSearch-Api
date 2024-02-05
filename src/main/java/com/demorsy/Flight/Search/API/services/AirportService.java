package com.demorsy.Flight.Search.API.services;

import com.demorsy.Flight.Search.API.entities.Airport;
import com.demorsy.Flight.Search.API.repos.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    private AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport saveAirport(Airport newAirport) {
        return airportRepository.save(newAirport);
    }

    public Airport getOneAirportById(String airportId) {
        return airportRepository.findById(airportId).orElse(null);
    }

    public Airport updateAirport(String airportId, Airport updatedairport) {
        Optional<Airport> airport = airportRepository.findById(airportId);
        if(airport.isPresent()){
            Airport foundAirport = airport.get();
            foundAirport.setId(updatedairport.getId());
            foundAirport.setCity(updatedairport.getCity());
            airportRepository.save(foundAirport);
            return foundAirport;
        }else{
            return null;
        }
    }

    public void deleteById(String airportId){
        airportRepository.deleteById(airportId);
    }
}
