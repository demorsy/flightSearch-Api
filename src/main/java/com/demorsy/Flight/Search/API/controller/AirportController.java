package com.demorsy.Flight.Search.API.controller;

import com.demorsy.Flight.Search.API.entities.Airport;
import com.demorsy.Flight.Search.API.services.AirportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public List<Airport> getAllAirports(){
        return airportService.getAllAirports();
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport newAirport){
        return airportService.saveAirport(newAirport);
    }

    @GetMapping("/{airportId}")
    public Airport getOneAirport(@PathVariable String airportId){
        return airportService.getOneAirportById(airportId);
    }

    @PutMapping("/{airportId}")
    public Airport updateAirport(@PathVariable String airportId, @RequestBody Airport updatedairport){
        return airportService.updateAirport(airportId,updatedairport);
    }

    @DeleteMapping("/{airportId}")
    public void deleteOneUser(@PathVariable String airportId){
        airportService.deleteById(airportId);
    }
}
