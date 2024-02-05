package com.demorsy.Flight.Search.API.repos;

import com.demorsy.Flight.Search.API.entities.Airport;
import com.demorsy.Flight.Search.API.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, String> {

    @Query("SELECT f FROM Flight f WHERE f.id = :flightId")
    List<Flight> findByflightId(@Param("flightId") String flightId);

    @Query("SELECT f FROM Flight f WHERE f.departure = :departureAirport AND f.arrival = :arrivalAirport " +
            "AND DAY(f.departure_date) = DAY(:departureDate)" +
            "AND MONTH(f.departure_date) = MONTH(:departureDate)" +
            "AND YEAR(f.departure_date) = YEAR(:departureDate) " )
    List<Flight> findFlightsWithArrivalTime(@Param("departureAirport") Airport departureAirport,
                                            @Param("arrivalAirport")Airport arrivalAirport,
                                            @Param("departureDate") Date departure_date
                                            );

    @Query("SELECT f FROM Flight f WHERE f.departure = :departureAirport AND f.arrival = :arrivalAirport " +
            "AND DAY(f.departure_date) = DAY(:returnDate)" +
            "AND MONTH(f.departure_date) = MONTH(:returnDate)" +
            "AND YEAR(f.departure_date) = YEAR(:returnDate)" )
    List<Flight> findFlightsWithArrivalTimeForReturnDate(@Param("departureAirport") Airport departureAirport,
                                            @Param("arrivalAirport")Airport arrivalAirport,
                                            @Param("returnDate") Date returnDate);

    @Query("SELECT f FROM Flight f WHERE f.departure = :departureAirport AND f.arrival = :arrivalAirport " +
            "AND DAY(f.departure_date) = DAY(:departureDate)" +
            "AND MONTH(f.departure_date) = MONTH(:departureDate)" +
            "AND YEAR(f.departure_date) = YEAR(:departureDate)")
    List<Flight> findFlightsWithoutArrivalTime(@Param("departureAirport") Airport departureAirport,
                                               @Param("arrivalAirport")Airport arrivalAirport,
                                               @Param("departureDate") Date departure_date);
}
