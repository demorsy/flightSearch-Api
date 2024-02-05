package com.demorsy.Flight.Search.API.MockGenerator;

import com.demorsy.Flight.Search.API.entities.Airport;
import com.demorsy.Flight.Search.API.entities.Flight;
import com.demorsy.Flight.Search.API.services.AirportService;
import com.demorsy.Flight.Search.API.services.FlightService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
@Component
public class MockFlightDataGenerator {

    private AirportService airportService;

    public MockFlightDataGenerator(AirportService airportService) {
        this.airportService = airportService;
    }

    private static final String[] AIRPORT_CODES = {"SAW", "LTBA", "ADB"};
    private static final String[] AIRLINES = {"TK", "PG", "LH", "BA", "AF", "DL"};

    public List<Flight> generateMockFlights(int numberOfFlights) {
        List<Flight> flights = new ArrayList<>();
        Random random = new Random();


        for (int i = 0; i < numberOfFlights; i++) {
            String flightNumber = generateRandomFlightNumber();
            String departure = generateRandomAirportCode(random);
            String destination = generateRandomAirportCode(random);
            Date departureDate = generateRandomDate();
            Date returnDate = generateReturnDate(departureDate); // Max 30 gün sonraya dönüş
            Flight newflight = new Flight();
            newflight.setId(flightNumber);

            Airport depairport = airportService.getOneAirportById(departure);
            if(depairport == null){
                Airport newdepairport = new Airport();
                newdepairport.setId(departure);
                newdepairport.setCity("ExampleCity");
                airportService.saveAirport(newdepairport);
                newflight.setDeparture(newdepairport);
            }else{
                newflight.setDeparture(depairport);
            }

            Airport desairport = airportService.getOneAirportById(destination);
            if(depairport == null){
                Airport newdesairport = new Airport();
                newdesairport.setId(destination);
                newdesairport.setCity("ExampleCity");
                newflight.setArrival(newdesairport);
                airportService.saveAirport(newdesairport);
            }else{
                newflight.setArrival(desairport);
            }

            newflight.setPrice((long) random.nextInt(600,3500));
            newflight.setDeparture_date(departureDate);
            newflight.setReturn_date(returnDate);
            flights.add(newflight);
        }

        return flights;
    }

    private String generateRandomFlightNumber() {
        Random random = new Random();
        String airlineCode = AIRLINES[random.nextInt(AIRLINES.length)];
        int flightNumber = random.nextInt(10000) + 1000;
        return airlineCode + flightNumber;
    }

    private String generateRandomAirportCode(Random random) {
        return AIRPORT_CODES[random.nextInt(AIRPORT_CODES.length)];
    }

    private Date generateRandomDate() {
        Random random = new Random();
        int year = 2023; // Assuming this year
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // Let's assume all months have 28 days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String dateString = String.format("%04d-%02d-%02dT%02d:%02d", year, month, day,
                random.nextInt(24), random.nextInt(60));
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(); // Default value in case of error
        }
    }

    private Date generateReturnDate(Date departuredate) {
        Random random = new Random();

        LocalDate departureLocalDate = departuredate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate returnLocalDate = departureLocalDate.plusDays(random.nextInt(30));

        LocalDateTime returnLocalDateTime = LocalDateTime.of(returnLocalDate, LocalTime.of(0,0));

        Date returnDate = Date.from(returnLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return returnDate;
    }
}
