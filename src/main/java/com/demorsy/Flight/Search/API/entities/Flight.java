package com.demorsy.Flight.Search.API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="Flight")
@Data
public class Flight {

    @Id
    String id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Airport departure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    @JsonIgnore
    Airport arrival;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'H:m")
    Date departure_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    Date return_date;

    Long price;

}
