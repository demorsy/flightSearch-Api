package com.demorsy.Flight.Search.API.exceptions;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(){
        super();
    }
    public FlightNotFoundException(String message){
        super(message);
    }
}
