package com.demorsy.Flight.Search.API.services;

import com.demorsy.Flight.Search.API.MockGenerator.MockFlightDataGenerator;
import com.demorsy.Flight.Search.API.dto.CreateFlightDto;
import com.demorsy.Flight.Search.API.dto.UpdateFlightDto;
import com.demorsy.Flight.Search.API.entities.Airport;
import com.demorsy.Flight.Search.API.entities.Flight;

import com.demorsy.Flight.Search.API.repos.FlightRepository;
import com.demorsy.Flight.Search.API.responses.FlightResponse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private FlightRepository flightRepository;
    private AirportService airportService;
    private MockFlightDataGenerator mockFlightDataGenerator;

    public FlightService(FlightRepository flightRepository, AirportService airportService,  MockFlightDataGenerator mockFlightDataGenerator) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
        this.mockFlightDataGenerator = mockFlightDataGenerator;

    }

    public List<FlightResponse> getAllFlightsWithParam(Optional<String> flightId,
                                                       Optional<String> departureAirport,
                                                       Optional<String> arrivalAirport,
                                                       Optional<String> departureDate,
                                                       Optional<String> returnDate) throws Exception {
        List<Flight> flights;
        if(flightId.isPresent()){
            flights = flightRepository.findByflightId(flightId.get());
        } else if(departureAirport.isPresent() && arrivalAirport.isPresent() &&
                departureDate.isPresent() && returnDate.isPresent()){

            Airport dep_airport = airportService.getOneAirportById(departureAirport.get());
            Airport arr_airport = airportService.getOneAirportById(arrivalAirport.get());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String dep_date_str = departureDate.get();
            Date dep_date = dateFormat.parse(dep_date_str);

            String arr_date_str = returnDate.get();
            Date return_date = dateFormat.parse(arr_date_str);

            List<Flight> depFlights = flightRepository.findFlightsWithArrivalTime(dep_airport,arr_airport,
                    dep_date);


            List<Flight> returnFlights = flightRepository.findFlightsWithArrivalTimeForReturnDate(arr_airport,dep_airport,
                    return_date);
            if(returnFlights.isEmpty()){
               return null;

            }
            depFlights.addAll(returnFlights);
            flights = depFlights;

        } else if(departureAirport.isPresent() && arrivalAirport.isPresent() &&
                departureDate.isPresent()){

            Airport dep_airport = airportService.getOneAirportById(departureAirport.get());
            Airport arr_airport = airportService.getOneAirportById(arrivalAirport.get());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            String dep_time_str = departureDate.get();
            Date dep_time = dateFormat.parse(dep_time_str);
            flights = flightRepository.findFlightsWithoutArrivalTime(dep_airport,arr_airport,
                    dep_time);

        }else{
            flights = flightRepository.findAll();
        }
        return  flights.stream().map(flight -> new FlightResponse(flight)).collect(Collectors.toList());
    }


    public Flight getFlightById(String flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    public Flight createFlight(CreateFlightDto createFlightRequest) throws Exception {
        Airport dep_airport = airportService.getOneAirportById(createFlightRequest.getDeparture_airport_id());
        Airport arr_airport = airportService.getOneAirportById(createFlightRequest.getArrival_airport_id());
        Optional<Flight> existFlight = flightRepository.findById(createFlightRequest.getId());
        if(existFlight.isPresent()){
            return null;

        }
        if(arr_airport == null || dep_airport == null){
            return null;
        }
        Flight newFlight = new Flight();
        newFlight.setId(createFlightRequest.getId());
        newFlight.setDeparture(dep_airport);
        newFlight.setArrival(arr_airport);
        newFlight.setPrice(createFlightRequest.getPrice());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        String dep_date_str = createFlightRequest.getDeparture_date();
        Date dep_date = dateFormat.parse(dep_date_str);
        newFlight.setDeparture_date(dep_date);

        String return_date_str = createFlightRequest.getReturn_date();
        Date return_date = dateFormat.parse(return_date_str);
        newFlight.setReturn_date((return_date));

        return flightRepository.save(newFlight);
    }

    public Flight updateFlightById(String flightId, UpdateFlightDto updateFlightRequest) throws Exception {
        Optional<Flight> flight = flightRepository.findById(flightId);
        Airport dep_airport = airportService.getOneAirportById(updateFlightRequest.getDeparture_airport_id());
        Airport arr_airport = airportService.getOneAirportById(updateFlightRequest.getArrival_airport_id());
        if(arr_airport == null || dep_airport == null){
            return null;
        }
        if(flight.isPresent()){
            Flight toUpdate = flight.get();
            toUpdate.setArrival(arr_airport);
            toUpdate.setDeparture(dep_airport);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            String dep_date_str = updateFlightRequest.getDeparture_time();
            Date dep_date = dateFormat.parse(dep_date_str);
            toUpdate.setDeparture_date(dep_date);

            String return_date_str = updateFlightRequest.getArrival_time();
            Date return_date = dateFormat.parse(return_date_str);
            toUpdate.setReturn_date((return_date));

            toUpdate.setPrice(updateFlightRequest.getPrice());

            flightRepository.save(toUpdate);
            return  toUpdate;
        }
        return null;
    }

    @Scheduled(cron = "0 0 0 * * *") // Her gece 00 da çalışır
    public void fetchAndSaveFlights() {
        List<Flight> mockFlights = mockFlightDataGenerator.generateMockFlights(20);

        List<Flight> flightsToSave = new ArrayList<>();
        for (Flight mockFlight : mockFlights) {
            if (!flightRepository.existsById(mockFlight.getId())) {
                flightsToSave.add(mockFlight);
            }
        }

        if (!flightsToSave.isEmpty()) {
            flightRepository.saveAll(flightsToSave);
        }
    }

    public void deleteFlightById(String flightId) {
        flightRepository.deleteById(flightId);
    }
}
