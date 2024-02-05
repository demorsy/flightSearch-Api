package com.demorsy.Flight.Search.API.dto;

import lombok.Data;

@Data
public class CreateFlightDto {

    String id;
    String departure_airport_id;
    String arrival_airport_id;
    String departure_date;
    String return_date;
    Long price;
}
