package com.demorsy.Flight.Search.API.controller;

import com.demorsy.Flight.Search.API.dto.CreateFlightDto;
import com.demorsy.Flight.Search.API.dto.UpdateFlightDto;
import com.demorsy.Flight.Search.API.entities.Flight;
import com.demorsy.Flight.Search.API.exceptions.FlightNotFoundException;
import com.demorsy.Flight.Search.API.responses.FlightResponse;
import com.demorsy.Flight.Search.API.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightResponse> getAllFlights(@RequestParam Optional<String> flightId,
                                              @RequestParam Optional<String> departureAirport,
                                              @RequestParam Optional<String> arrivalAirport,
                                              @RequestParam Optional<String> departureDate,
                                              @RequestParam Optional<String> returnDate) throws Exception {
        return flightService.getAllFlightsWithParam(flightId,departureAirport,arrivalAirport,departureDate,returnDate);
    }

    @GetMapping("/{flightId}")
    public Flight getFlightById(@PathVariable String flightId){

        Flight flight = flightService.getFlightById(flightId);
        if(flight == null){
            throw new FlightNotFoundException();
        }
        return flight;
    }

    @PostMapping
    public Flight createFlight(@RequestBody CreateFlightDto createFlightRequest) throws Exception {
        return flightService.createFlight(createFlightRequest);
    }

    @PutMapping("/{flightId}")
    public Flight updateFlightById(@PathVariable String flightId, @RequestBody UpdateFlightDto updateFlightRequest) throws Exception {
        return flightService.updateFlightById(flightId,updateFlightRequest);
    }

    @DeleteMapping("/{flightId}")
    public void deleteFlightById(@PathVariable String flightId){
         flightService.deleteFlightById(flightId);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handeFlightNotFound(){

    }
}
