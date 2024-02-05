package com.demorsy.Flight.Search.API.responses;

import com.demorsy.Flight.Search.API.entities.Flight;
import lombok.Data;

@Data
public class FlightResponse {
    String id;
    String departure_airport_id;
    String arrival_airport_id;
    String departure_date;
    Long price;

    public FlightResponse(Flight entity){
        this.id = entity.getId();
        this.departure_airport_id = entity.getDeparture().getId();
        this.arrival_airport_id = entity.getArrival().getId();
        this.departure_date = entity.getDeparture_date().toString();
        this.price = entity.getPrice();
    }

}
