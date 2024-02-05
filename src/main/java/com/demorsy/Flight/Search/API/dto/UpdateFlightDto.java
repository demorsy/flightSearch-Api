package com.demorsy.Flight.Search.API.dto;

import lombok.Data;

@Data
public class UpdateFlightDto {
    String departure_airport_id;
    String arrival_airport_id;
    String departure_time;
    String arrival_time;
    Long price;
}
